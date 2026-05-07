package com.clinica.expedientes.service;

import com.clinica.expedientes.dto.request.RechazarReporteRequest;
import com.clinica.expedientes.dto.response.ReportePendienteResponse;
import com.clinica.expedientes.dto.response.ReporteResponse;
import com.clinica.expedientes.exception.AccesoDenegadoException;
import com.clinica.expedientes.exception.EstadoInvalidoException;
import com.clinica.expedientes.exception.RecursoNoEncontradoException;
import com.clinica.expedientes.model.RegistroAuditoria;
import com.clinica.expedientes.model.ReporteSesion;
import com.clinica.expedientes.model.SupervisorTerapeuta;
import com.clinica.expedientes.model.UsuarioCache;
import com.clinica.expedientes.repository.ReporteSesionRepository;
import com.clinica.expedientes.repository.SupervisorTerapeutaRepository;
import com.clinica.expedientes.repository.UsuarioCacheRepository;
import com.clinica.expedientes.security.UsuarioAutenticado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupervisorService {

    private final ReporteSesionRepository reporteSesionRepository;
    private final SupervisorTerapeutaRepository supervisorTerapeutaRepository;
    private final UsuarioCacheRepository usuarioCacheRepository;
    private final AuditoriaService auditoriaService;

    public List<ReportePendienteResponse> listarPendientes(UsuarioAutenticado supervisor,
                                                            Long idTerapeuta,
                                                            LocalDate fechaDesde,
                                                            LocalDate fechaHasta) {
        List<Long> idsTerapeuta = supervisorTerapeutaRepository.findByIdSupervisor(supervisor.getIdUsuario())
                .stream().map(SupervisorTerapeuta::getIdTerapeuta).toList();

        return reporteSesionRepository
                .findPendientesPorSupervisor(idsTerapeuta, idTerapeuta, fechaDesde, fechaHasta)
                .stream()
                .map(r -> {
                    String nombreTerapeuta = usuarioCacheRepository.findById(r.getIdTerapeuta())
                            .map(UsuarioCache::getNombreCompleto).orElse("Desconocido");
                    return new ReportePendienteResponse(r.getIdDocumento(), r.getIdTerapeuta(),
                            nombreTerapeuta, r.getFechaSesion());
                }).toList();
    }

    public ReporteResponse verReporte(Long idReporte, UsuarioAutenticado supervisor) {
        ReporteSesion reporte = obtenerReporteBajoSupervision(idReporte, supervisor);
        return construirReporteResponse(reporte);
    }

    @Transactional
    public ReporteResponse aprobarReporte(Long idReporte, UsuarioAutenticado supervisor) {
        ReporteSesion reporte = obtenerReporteBajoSupervision(idReporte, supervisor);

        if (reporte.getEstado() != ReporteSesion.EstadoReporte.PENDIENTE)
            throw new EstadoInvalidoException("El reporte no se encuentra en estado PENDIENTE.");

        reporte.setEstado(ReporteSesion.EstadoReporte.APROBADO);
        reporte.setFechaModificacion(LocalDateTime.now());
        reporteSesionRepository.save(reporte);

        auditoriaService.registrar(supervisor, RegistroAuditoria.AccionAuditoria.APROBAR_REPORTE,
                "ReporteSesion", String.valueOf(idReporte), RegistroAuditoria.ResultadoAuditoria.PERMITIDO);

        return ReporteResponse.builder()
                .idDocumento(reporte.getIdDocumento())
                .estado(reporte.getEstado())
                .fechaModificacion(reporte.getFechaModificacion())
                .build();
    }

    @Transactional
    public ReporteResponse rechazarReporte(Long idReporte, RechazarReporteRequest req, UsuarioAutenticado supervisor) {
        ReporteSesion reporte = obtenerReporteBajoSupervision(idReporte, supervisor);

        if (reporte.getEstado() != ReporteSesion.EstadoReporte.PENDIENTE)
            throw new EstadoInvalidoException("El reporte no se encuentra en estado PENDIENTE.");

        reporte.setEstado(ReporteSesion.EstadoReporte.RECHAZADO);
        reporte.setComentariosSupervisor(req.getComentariosSupervisor());
        reporte.setFechaModificacion(LocalDateTime.now());
        reporteSesionRepository.save(reporte);

        auditoriaService.registrar(supervisor, RegistroAuditoria.AccionAuditoria.RECHAZAR_REPORTE,
                "ReporteSesion", String.valueOf(idReporte), RegistroAuditoria.ResultadoAuditoria.PERMITIDO);

        return ReporteResponse.builder()
                .idDocumento(reporte.getIdDocumento())
                .estado(reporte.getEstado())
                .comentariosSupervisor(reporte.getComentariosSupervisor())
                .fechaModificacion(reporte.getFechaModificacion())
                .build();
    }

    private ReporteSesion obtenerReporteBajoSupervision(Long idReporte, UsuarioAutenticado supervisor) {
        ReporteSesion reporte = reporteSesionRepository.findById(idReporte)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reporte no encontrado"));

        boolean supervisa = supervisorTerapeutaRepository.existsByIdSupervisorAndIdTerapeuta(
                supervisor.getIdUsuario(), reporte.getIdTerapeuta());

        if (!supervisa)
            throw new AccesoDenegadoException("El supervisor no supervisa al terapeuta del reporte.");

        return reporte;
    }

    private ReporteResponse construirReporteResponse(ReporteSesion reporte) {
        String nombreTerapeuta = usuarioCacheRepository.findById(reporte.getIdTerapeuta())
                .map(UsuarioCache::getNombreCompleto).orElse("Desconocido");
        String nombrePaciente = usuarioCacheRepository.findById(reporte.getExpediente().getIdPaciente())
                .map(UsuarioCache::getNombreCompleto).orElse("Desconocido");

        return ReporteResponse.builder()
                .idDocumento(reporte.getIdDocumento())
                .idExpediente(reporte.getExpediente().getIdExpediente())
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
