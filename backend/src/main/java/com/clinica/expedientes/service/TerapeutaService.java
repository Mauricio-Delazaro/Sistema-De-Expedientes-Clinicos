package com.clinica.expedientes.service;

import com.clinica.expedientes.dto.request.CrearReporteRequest;
import com.clinica.expedientes.dto.response.ExpedienteResponse;
import com.clinica.expedientes.dto.response.PacienteAsignadoResponse;
import com.clinica.expedientes.dto.response.ReporteResponse;
import com.clinica.expedientes.exception.AccesoDenegadoException;
import com.clinica.expedientes.exception.EstadoInvalidoException;
import com.clinica.expedientes.exception.RecursoNoEncontradoException;
import com.clinica.expedientes.model.*;
import com.clinica.expedientes.repository.*;
import com.clinica.expedientes.security.UsuarioAutenticado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TerapeutaService {

    private final ExpedienteRepository expedienteRepository;
    private final ReporteSesionRepository reporteSesionRepository;
    private final UsuarioCacheRepository usuarioCacheRepository;
    private final AuditoriaService auditoriaService;

    public List<PacienteAsignadoResponse> listarPacientes(UsuarioAutenticado terapeuta) {
        return expedienteRepository.findByIdTerapeuta(terapeuta.getIdUsuario()).stream()
                .map(exp -> {
                    String nombre = usuarioCacheRepository.findById(exp.getIdPaciente())
                            .map(UsuarioCache::getNombreCompleto).orElse("Desconocido");
                    return new PacienteAsignadoResponse(exp.getIdPaciente(), nombre, exp.getIdExpediente());
                }).toList();
    }

    @Transactional
    public ExpedienteResponse verExpediente(Long idExpediente, UsuarioAutenticado terapeuta) {
        Expediente exp = expedienteRepository.findById(idExpediente)
                .orElseThrow(() -> new RecursoNoEncontradoException("Expediente no encontrado"));

        boolean asignado = exp.getIdTerapeuta().equals(terapeuta.getIdUsuario());
        auditoriaService.registrar(terapeuta,
                RegistroAuditoria.AccionAuditoria.CONSULTAR_EXPEDIENTE,
                "Expediente", String.valueOf(idExpediente),
                asignado ? RegistroAuditoria.ResultadoAuditoria.PERMITIDO
                         : RegistroAuditoria.ResultadoAuditoria.DENEGADO);

        if (!asignado)
            throw new AccesoDenegadoException("El terapeuta no tiene asignación al expediente solicitado.");

        return construirExpedienteResponse(exp);
    }

    @Transactional
    public ReporteResponse crearReporte(Long idExpediente, CrearReporteRequest req, UsuarioAutenticado terapeuta) {
        Expediente exp = expedienteRepository.findById(idExpediente)
                .orElseThrow(() -> new RecursoNoEncontradoException("Expediente no encontrado"));

        if (!exp.getIdTerapeuta().equals(terapeuta.getIdUsuario()))
            throw new AccesoDenegadoException("El terapeuta no tiene asignación al expediente solicitado.");

        if (exp.getEstado() == Expediente.EstadoExpediente.ARCHIVADO)
            throw new EstadoInvalidoException("No se puede registrar un reporte en un expediente archivado.");

        ReporteSesion reporte = new ReporteSesion();
        reporte.setExpediente(exp);
        reporte.setFecha(LocalDate.now());
        reporte.setTipo(Documento.TipoDocumento.REPORTE_SESION);
        reporte.setIdTerapeuta(terapeuta.getIdUsuario());
        reporte.setFechaSesion(req.getFechaSesion());
        reporte.setDuracionSesion(req.getDuracionSesion());
        reporte.setObservacionesClinicas(req.getObservacionesClinicas());
        reporte.setComentariosTerapeuta(req.getComentariosTerapeuta());
        reporte.setEstado(ReporteSesion.EstadoReporte.CREADO);
        reporte.setFechaCreacion(LocalDateTime.now());
        reporte.setFechaModificacion(LocalDateTime.now());
        reporteSesionRepository.save(reporte);

        auditoriaService.registrar(terapeuta,
                RegistroAuditoria.AccionAuditoria.REGISTRAR_REPORTE,
                "ReporteSesion", String.valueOf(reporte.getIdDocumento()),
                RegistroAuditoria.ResultadoAuditoria.PERMITIDO);

        return construirReporteResponse(reporte, exp);
    }

    @Transactional
    public ReporteResponse enviarReporte(Long idReporte, UsuarioAutenticado terapeuta) {
        ReporteSesion reporte = obtenerReportePropio(idReporte, terapeuta);

        if (reporte.getEstado() != ReporteSesion.EstadoReporte.CREADO
                && reporte.getEstado() != ReporteSesion.EstadoReporte.RECHAZADO)
            throw new EstadoInvalidoException("El reporte no se encuentra en estado CREADO ni RECHAZADO.");

        reporte.setEstado(ReporteSesion.EstadoReporte.PENDIENTE);
        reporte.setFechaModificacion(LocalDateTime.now());
        reporteSesionRepository.save(reporte);

        auditoriaService.registrar(terapeuta, RegistroAuditoria.AccionAuditoria.ENVIAR_REPORTE,
                "ReporteSesion", String.valueOf(idReporte), RegistroAuditoria.ResultadoAuditoria.PERMITIDO);

        return ReporteResponse.builder()
                .idDocumento(reporte.getIdDocumento())
                .estado(reporte.getEstado())
                .fechaModificacion(reporte.getFechaModificacion())
                .build();
    }

    @Transactional
    public ReporteResponse modificarReporte(Long idReporte, CrearReporteRequest req, UsuarioAutenticado terapeuta) {
        ReporteSesion reporte = obtenerReportePropio(idReporte, terapeuta);

        if (reporte.getEstado() != ReporteSesion.EstadoReporte.RECHAZADO)
            throw new EstadoInvalidoException("Solo se pueden modificar reportes en estado RECHAZADO.");

        reporte.setFechaSesion(req.getFechaSesion());
        reporte.setDuracionSesion(req.getDuracionSesion());
        reporte.setObservacionesClinicas(req.getObservacionesClinicas());
        reporte.setComentariosTerapeuta(req.getComentariosTerapeuta());
        reporte.setFechaModificacion(LocalDateTime.now());
        reporteSesionRepository.save(reporte);

        auditoriaService.registrar(terapeuta, RegistroAuditoria.AccionAuditoria.MODIFICAR_REPORTE,
                "ReporteSesion", String.valueOf(idReporte), RegistroAuditoria.ResultadoAuditoria.PERMITIDO);

        return construirReporteResponse(reporte, reporte.getExpediente());
    }

    public ReporteResponse verReporte(Long idReporte, UsuarioAutenticado terapeuta) {
        ReporteSesion reporte = obtenerReportePropio(idReporte, terapeuta);
        return construirReporteResponse(reporte, reporte.getExpediente());
    }

    private ReporteSesion obtenerReportePropio(Long idReporte, UsuarioAutenticado terapeuta) {
        ReporteSesion reporte = reporteSesionRepository.findById(idReporte)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reporte no encontrado"));
        if (!reporte.getIdTerapeuta().equals(terapeuta.getIdUsuario()))
            throw new AccesoDenegadoException("El terapeuta no es propietario del reporte.");
        return reporte;
    }

    private ExpedienteResponse construirExpedienteResponse(Expediente exp) {
        String nombrePaciente = usuarioCacheRepository.findById(exp.getIdPaciente())
                .map(UsuarioCache::getNombreCompleto).orElse("Desconocido");

        List<Documento> docs = exp.getDocumentos() == null ? List.of() : exp.getDocumentos();

        List<ExpedienteResponse.ReporteResumen> reportes = docs.stream()
                .filter(d -> d.getTipo() == Documento.TipoDocumento.REPORTE_SESION)
                .map(d -> (ReporteSesion) d)
                .map(r -> ExpedienteResponse.ReporteResumen.builder()
                        .idDocumento(r.getIdDocumento())
                        .fechaSesion(r.getFechaSesion())
                        .estado(r.getEstado())
                        .build())
                .toList();

        ExpedienteResponse.DocumentoResumen entrevista = docs.stream()
                .filter(d -> d.getTipo() == Documento.TipoDocumento.ENTREVISTA_SOCIOECONOMICA)
                .findFirst()
                .map(d -> ExpedienteResponse.DocumentoResumen.builder()
                        .idDocumento(d.getIdDocumento()).fecha(d.getFecha()).build())
                .orElse(null);

        ExpedienteResponse.DocumentoResumen consentimiento = docs.stream()
                .filter(d -> d.getTipo() == Documento.TipoDocumento.INFORME_CONSENTIMIENTO)
                .findFirst()
                .map(d -> ExpedienteResponse.DocumentoResumen.builder()
                        .idDocumento(d.getIdDocumento()).fecha(d.getFecha()).build())
                .orElse(null);

        return ExpedienteResponse.builder()
                .idExpediente(exp.getIdExpediente())
                .estado(exp.getEstado())
                .fechaProxCita(exp.getFechaProxCita())
                .paciente(ExpedienteResponse.PacienteInfo.builder()
                        .idPaciente(exp.getIdPaciente())
                        .nombreCompleto(nombrePaciente)
                        .build())
                .entrevistaSocioeconomica(entrevista)
                .informeConsentimiento(consentimiento)
                .reportesSesion(reportes)
                .build();
    }

    private ReporteResponse construirReporteResponse(ReporteSesion reporte, Expediente exp) {
        String nombreTerapeuta = usuarioCacheRepository.findById(reporte.getIdTerapeuta())
                .map(UsuarioCache::getNombreCompleto).orElse("Desconocido");
        String nombrePaciente = usuarioCacheRepository.findById(exp.getIdPaciente())
                .map(UsuarioCache::getNombreCompleto).orElse("Desconocido");

        return ReporteResponse.builder()
                .idDocumento(reporte.getIdDocumento())
                .idExpediente(exp.getIdExpediente())
                .idTerapeuta(reporte.getIdTerapeuta())
                .nombreTerapeuta(nombreTerapeuta)
                .nombrePaciente(nombrePaciente)
                .fechaSesion(reporte.getFechaSesion())
                .duracionSesion(reporte.getDuracionSesion())
                .observacionesClinicas(reporte.getObservacionesClinicas())
                .comentariosTerapeuta(reporte.getComentariosTerapeuta())
                .comentariosSupervisor(reporte.getComentariosSupervisor())
                .estado(reporte.getEstado())
                .fechaCreacion(reporte.getFechaCreacion())
                .fechaModificacion(reporte.getFechaModificacion())
                .build();
    }
}
