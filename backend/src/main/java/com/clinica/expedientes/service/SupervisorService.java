package com.clinica.expedientes.service;

import com.clinica.expedientes.dto.request.RechazarReporteRequest;
import com.clinica.expedientes.dto.response.*;
import com.clinica.expedientes.exception.AccesoDenegadoException;
import com.clinica.expedientes.exception.EstadoInvalidoException;
import com.clinica.expedientes.exception.RecursoNoEncontradoException;
import com.clinica.expedientes.model.*;
import com.clinica.expedientes.model.enums.*;
import com.clinica.expedientes.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupervisorService {

    private final UsuarioRepository usuarioRepo;
    private final ReporteSesionRepository reporteRepo;
    private final AuditoriaService auditoriaService;

    public SupervisorService(UsuarioRepository usuarioRepo, ReporteSesionRepository reporteRepo,
                              AuditoriaService auditoriaService) {
        this.usuarioRepo = usuarioRepo;
        this.reporteRepo = reporteRepo;
        this.auditoriaService = auditoriaService;
    }

    private Supervisor resolverSupervisor(Long userId) {
        Usuario u = usuarioRepo.findById(userId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado: " + userId));
        if (!(u instanceof Supervisor s)) {
            throw new AccesoDenegadoException("El usuario no tiene rol SUPERVISOR.");
        }
        return s;
    }

    @Transactional(readOnly = true)
    public List<ReportePendienteResponse> getMisReportesPendientes(Long userId, Long idTerapeuta,
                                                                    LocalDate fechaDesde, LocalDate fechaHasta) {
        Supervisor supervisor = resolverSupervisor(userId);
        return reporteRepo.findByEstadoAndSupervisor(
                        EstadoReporte.PENDIENTE, supervisor.getIdUsuario(), idTerapeuta, fechaDesde, fechaHasta)
                .stream()
                .map(r -> new ReportePendienteResponse(
                        r.getIdDocumento(),
                        r.getTerapeuta().getIdUsuario(),
                        r.getTerapeuta().getNombreCompleto(),
                        r.getFechaSesion()
                ))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ReporteDetalleResponse getReporte(Long userId, Long idReporte) {
        Usuario usuario = usuarioRepo.findById(userId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado: " + userId));

        ReporteSesion reporte = reporteRepo.findById(idReporte)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reporte no encontrado: " + idReporte));

        if (usuario instanceof Terapeuta t) {
            if (!reporte.getTerapeuta().getIdUsuario().equals(t.getIdUsuario())) {
                throw new AccesoDenegadoException("El terapeuta no es propietario de este reporte.");
            }
        } else if (usuario instanceof Supervisor s) {
            if (!reporteRepo.supervisorTieneAccesoAReporte(idReporte, s.getIdUsuario())) {
                throw new AccesoDenegadoException("El supervisor no supervisa al terapeuta de este reporte.");
            }
        } else {
            throw new AccesoDenegadoException("Acceso no autorizado a este recurso.");
        }

        Expediente exp = reporte.getExpediente();
        return new ReporteDetalleResponse(
                reporte.getIdDocumento(),
                exp.getIdExpediente(),
                reporte.getTerapeuta().getIdUsuario(),
                reporte.getTerapeuta().getNombreCompleto(),
                exp.getPaciente().getNombreCompleto(),
                reporte.getFechaSesion(),
                reporte.getDuracionSesion(),
                reporte.getObservacionesClinicas(),
                reporte.getComentariosTerapeuta(),
                reporte.getComentariosSupervisor(),
                reporte.getEstado().name(),
                reporte.getFechaCreacion(),
                reporte.getFechaModificacion()
        );
    }

    @Transactional
    public ReporteEstadoResponse aprobarReporte(Long userId, Long idReporte) {
        Supervisor supervisor = resolverSupervisor(userId);

        ReporteSesion reporte = reporteRepo.findById(idReporte)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reporte no encontrado: " + idReporte));

        if (!reporteRepo.supervisorTieneAccesoAReporte(idReporte, supervisor.getIdUsuario())) {
            throw new AccesoDenegadoException("El supervisor no supervisa al terapeuta de este reporte.");
        }

        if (reporte.getEstado() != EstadoReporte.PENDIENTE) {
            throw new EstadoInvalidoException("Solo se pueden aprobar reportes en estado PENDIENTE.");
        }

        reporte.setEstado(EstadoReporte.APROBADO);
        reporte.setFechaModificacion(OffsetDateTime.now());
        reporteRepo.save(reporte);

        auditoriaService.registrar(userId, RolUsuario.SUPERVISOR, AccionAuditoria.APROBAR_REPORTE,
                "ReporteSesion", String.valueOf(idReporte), ResultadoAuditoria.PERMITIDO);

        return new ReporteEstadoResponse(reporte.getIdDocumento(), reporte.getEstado().name(),
                reporte.getFechaModificacion());
    }

    @Transactional
    public ReporteRechazadoResponse rechazarReporte(Long userId, Long idReporte, RechazarReporteRequest req) {
        Supervisor supervisor = resolverSupervisor(userId);

        ReporteSesion reporte = reporteRepo.findById(idReporte)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reporte no encontrado: " + idReporte));

        if (!reporteRepo.supervisorTieneAccesoAReporte(idReporte, supervisor.getIdUsuario())) {
            throw new AccesoDenegadoException("El supervisor no supervisa al terapeuta de este reporte.");
        }

        if (reporte.getEstado() != EstadoReporte.PENDIENTE) {
            throw new EstadoInvalidoException("Solo se pueden rechazar reportes en estado PENDIENTE.");
        }

        reporte.setEstado(EstadoReporte.RECHAZADO);
        reporte.setComentariosSupervisor(req.getComentariosSupervisor());
        reporte.setFechaModificacion(OffsetDateTime.now());
        reporteRepo.save(reporte);

        auditoriaService.registrar(userId, RolUsuario.SUPERVISOR, AccionAuditoria.RECHAZAR_REPORTE,
                "ReporteSesion", String.valueOf(idReporte), ResultadoAuditoria.PERMITIDO);

        return new ReporteRechazadoResponse(
                reporte.getIdDocumento(), reporte.getEstado().name(),
                reporte.getComentariosSupervisor(), reporte.getFechaModificacion()
        );
    }
}
