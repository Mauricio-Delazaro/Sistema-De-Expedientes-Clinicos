package com.clinica.expedientes.service;

import com.clinica.expedientes.dto.request.CrearReporteRequest;
import com.clinica.expedientes.dto.response.*;
import com.clinica.expedientes.exception.AccesoDenegadoException;
import com.clinica.expedientes.exception.EstadoInvalidoException;
import com.clinica.expedientes.exception.RecursoNoEncontradoException;
import com.clinica.expedientes.model.*;
import com.clinica.expedientes.model.enums.*;
import com.clinica.expedientes.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TerapeutaService {

    private final UsuarioRepository usuarioRepo;
    private final ExpedienteRepository expedienteRepo;
    private final ReporteSesionRepository reporteRepo;
    private final AuditoriaService auditoriaService;

    public TerapeutaService(UsuarioRepository usuarioRepo, ExpedienteRepository expedienteRepo,
                             ReporteSesionRepository reporteRepo, AuditoriaService auditoriaService) {
        this.usuarioRepo = usuarioRepo;
        this.expedienteRepo = expedienteRepo;
        this.reporteRepo = reporteRepo;
        this.auditoriaService = auditoriaService;
    }

    private Terapeuta resolverTerapeuta(Long userId) {
        Usuario u = usuarioRepo.findById(userId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado: " + userId));
        if (!(u instanceof Terapeuta t)) {
            throw new AccesoDenegadoException("El usuario no tiene rol TERAPEUTA.");
        }
        return t;
    }

    @Transactional(readOnly = true)
    public List<PacienteResumenResponse> getMisPacientes(Long userId) {
        Terapeuta terapeuta = resolverTerapeuta(userId);
        return expedienteRepo.findByTerapeutaPaciente(terapeuta.getIdUsuario()).stream()
                .map(e -> new PacienteResumenResponse(
                        e.getPaciente().getIdUsuario(),
                        e.getPaciente().getNombreCompleto(),
                        e.getIdExpediente()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public ExpedienteDetalleResponse getExpediente(Long userId, Long idExpediente) {
        Terapeuta terapeuta = resolverTerapeuta(userId);

        boolean exists = expedienteRepo.existsById(idExpediente);
        boolean tieneAcceso = expedienteRepo.terapeutaTieneAccesoAExpediente(idExpediente, terapeuta.getIdUsuario());

        if (!tieneAcceso) {
            auditoriaService.registrar(userId, RolUsuario.TERAPEUTA, AccionAuditoria.CONSULTAR_EXPEDIENTE,
                    "Expediente", String.valueOf(idExpediente), ResultadoAuditoria.DENEGADO);
            if (!exists) throw new RecursoNoEncontradoException("Expediente no encontrado: " + idExpediente);
            throw new AccesoDenegadoException("El terapeuta no tiene asignación al expediente solicitado.");
        }

        Expediente exp = expedienteRepo.findById(idExpediente).get();
        auditoriaService.registrar(userId, RolUsuario.TERAPEUTA, AccionAuditoria.CONSULTAR_EXPEDIENTE,
                "Expediente", String.valueOf(idExpediente), ResultadoAuditoria.PERMITIDO);

        return mapToExpedienteDetalle(exp);
    }

    @Transactional
    public ReporteCreadoResponse crearReporte(Long userId, Long idExpediente, CrearReporteRequest req) {
        Terapeuta terapeuta = resolverTerapeuta(userId);

        boolean exists = expedienteRepo.existsById(idExpediente);
        boolean tieneAcceso = expedienteRepo.terapeutaTieneAccesoAExpediente(idExpediente, terapeuta.getIdUsuario());

        if (!tieneAcceso) {
            auditoriaService.registrar(userId, RolUsuario.TERAPEUTA, AccionAuditoria.REGISTRAR_REPORTE,
                    "Expediente", String.valueOf(idExpediente), ResultadoAuditoria.DENEGADO);
            if (!exists) throw new RecursoNoEncontradoException("Expediente no encontrado: " + idExpediente);
            throw new AccesoDenegadoException("El terapeuta no tiene asignación al expediente solicitado.");
        }

        Expediente expediente = expedienteRepo.findById(idExpediente).get();

        if (expediente.getEstado() == EstadoExpediente.ARCHIVADO) {
            throw new EstadoInvalidoException("No se pueden registrar reportes en un expediente archivado.");
        }

        OffsetDateTime ahora = OffsetDateTime.now();
        ReporteSesion reporte = new ReporteSesion();
        reporte.setExpediente(expediente);
        reporte.setTerapeuta(terapeuta);
        reporte.setFecha(ahora.toLocalDate());
        reporte.setFechaSesion(req.getFechaSesion());
        reporte.setDuracionSesion(req.getDuracionSesion());
        reporte.setObservacionesClinicas(req.getObservacionesClinicas());
        reporte.setComentariosTerapeuta(req.getComentariosTerapeuta());
        reporte.setEstado(EstadoReporte.CREADO);
        reporte.setFechaCreacion(ahora);
        reporte.setFechaModificacion(ahora);

        ReporteSesion saved = reporteRepo.save(reporte);

        auditoriaService.registrar(userId, RolUsuario.TERAPEUTA, AccionAuditoria.REGISTRAR_REPORTE,
                "ReporteSesion", String.valueOf(saved.getIdDocumento()), ResultadoAuditoria.PERMITIDO);

        return new ReporteCreadoResponse(
                saved.getIdDocumento(), idExpediente, saved.getFechaSesion(), saved.getDuracionSesion(),
                saved.getObservacionesClinicas(), saved.getComentariosTerapeuta(),
                saved.getEstado().name(), saved.getFechaCreacion(), saved.getFechaModificacion()
        );
    }

    @Transactional
    public ReporteEstadoResponse enviarReporte(Long userId, Long idReporte) {
        Terapeuta terapeuta = resolverTerapeuta(userId);

        ReporteSesion reporte = reporteRepo.findById(idReporte)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reporte no encontrado: " + idReporte));

        if (!reporte.getTerapeuta().getIdUsuario().equals(terapeuta.getIdUsuario())) {
            throw new AccesoDenegadoException("El terapeuta no es propietario de este reporte.");
        }

        if (reporte.getEstado() != EstadoReporte.CREADO && reporte.getEstado() != EstadoReporte.RECHAZADO) {
            throw new EstadoInvalidoException(
                    "El reporte debe estar en estado CREADO o RECHAZADO para enviarse a revisión.");
        }

        reporte.setEstado(EstadoReporte.PENDIENTE);
        reporte.setFechaModificacion(OffsetDateTime.now());
        reporteRepo.save(reporte);

        auditoriaService.registrar(userId, RolUsuario.TERAPEUTA, AccionAuditoria.ENVIAR_REPORTE,
                "ReporteSesion", String.valueOf(idReporte), ResultadoAuditoria.PERMITIDO);

        return new ReporteEstadoResponse(reporte.getIdDocumento(), reporte.getEstado().name(),
                reporte.getFechaModificacion());
    }

    @Transactional
    public ReporteModificadoResponse modificarReporte(Long userId, Long idReporte, CrearReporteRequest req) {
        Terapeuta terapeuta = resolverTerapeuta(userId);

        ReporteSesion reporte = reporteRepo.findById(idReporte)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reporte no encontrado: " + idReporte));

        if (!reporte.getTerapeuta().getIdUsuario().equals(terapeuta.getIdUsuario())) {
            throw new AccesoDenegadoException("El terapeuta no es propietario de este reporte.");
        }

        if (reporte.getEstado() != EstadoReporte.RECHAZADO) {
            throw new EstadoInvalidoException("Solo se pueden modificar reportes en estado RECHAZADO.");
        }

        reporte.setFechaSesion(req.getFechaSesion());
        reporte.setDuracionSesion(req.getDuracionSesion());
        reporte.setObservacionesClinicas(req.getObservacionesClinicas());
        reporte.setComentariosTerapeuta(req.getComentariosTerapeuta());
        reporte.setFechaModificacion(OffsetDateTime.now());
        reporteRepo.save(reporte);

        auditoriaService.registrar(userId, RolUsuario.TERAPEUTA, AccionAuditoria.MODIFICAR_REPORTE,
                "ReporteSesion", String.valueOf(idReporte), ResultadoAuditoria.PERMITIDO);

        return new ReporteModificadoResponse(
                reporte.getIdDocumento(), reporte.getFechaSesion(), reporte.getDuracionSesion(),
                reporte.getObservacionesClinicas(), reporte.getComentariosTerapeuta(),
                reporte.getComentariosSupervisor(), reporte.getEstado().name(), reporte.getFechaModificacion()
        );
    }

    private ExpedienteDetalleResponse mapToExpedienteDetalle(Expediente exp) {
        Paciente p = exp.getPaciente();
        PacienteDetalleDto pacienteDto = new PacienteDetalleDto(
                p.getIdUsuario(), p.getNombreCompleto(), p.getEdad(),
                p.getFechaNacimiento(), p.getCorreoElectronico(), p.getNumeroTelefonico()
        );

        DocumentoResumenDto entrevistaDto = null;
        if (exp.getEntrevistaSocioeconomica() != null) {
            EntrevistaSocioeconomica e = exp.getEntrevistaSocioeconomica();
            entrevistaDto = new DocumentoResumenDto(e.getIdDocumento(), e.getFecha());
        }

        DocumentoResumenDto consentimientoDto = null;
        if (exp.getInformeConsentimiento() != null) {
            InformeConsentimiento i = exp.getInformeConsentimiento();
            consentimientoDto = new DocumentoResumenDto(i.getIdDocumento(), i.getFecha());
        }

        List<ReporteResumenDto> reportes = exp.getReportesSesion() == null
                ? Collections.emptyList()
                : exp.getReportesSesion().stream()
                        .map(r -> new ReporteResumenDto(r.getIdDocumento(), r.getFechaSesion(), r.getEstado().name()))
                        .collect(Collectors.toList());

        return new ExpedienteDetalleResponse(
                exp.getIdExpediente(), exp.getEstado().name(), exp.getFechaProxCita(),
                pacienteDto, entrevistaDto, consentimientoDto, reportes
        );
    }
}
