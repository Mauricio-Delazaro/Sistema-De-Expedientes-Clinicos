package com.clinica.expedientes.service;

import com.clinica.expedientes.dto.response.LogAuditoriaResponse;
import com.clinica.expedientes.model.LogAuditoria;
import com.clinica.expedientes.model.enums.AccionAuditoria;
import com.clinica.expedientes.model.enums.ResultadoAuditoria;
import com.clinica.expedientes.model.enums.RolUsuario;
import com.clinica.expedientes.repository.LogAuditoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditoriaService {

    private final LogAuditoriaRepository logRepo;

    public AuditoriaService(LogAuditoriaRepository logRepo) {
        this.logRepo = logRepo;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrar(Long idUsuario, RolUsuario rol, AccionAuditoria accion,
                          String recurso, String idRecurso, ResultadoAuditoria resultado) {
        LogAuditoria log = new LogAuditoria();
        log.setIdUsuario(idUsuario);
        log.setRolUsuario(rol);
        log.setAccion(accion);
        log.setRecurso(recurso);
        log.setIdRecurso(idRecurso);
        log.setFechaHora(OffsetDateTime.now());
        log.setResultado(resultado);
        logRepo.save(log);
    }

    @Transactional(readOnly = true)
    public List<LogAuditoriaResponse> consultar(Long idUsuario, OffsetDateTime fechaDesde,
                                                 OffsetDateTime fechaHasta, String accionStr,
                                                 String recurso, String idRecurso, String resultadoStr) {
        AccionAuditoria accion = null;
        if (accionStr != null && !accionStr.isBlank()) {
            try {
                accion = AccionAuditoria.valueOf(accionStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Valor de acción inválido: " + accionStr);
            }
        }

        ResultadoAuditoria resultado = null;
        if (resultadoStr != null && !resultadoStr.isBlank()) {
            try {
                resultado = ResultadoAuditoria.valueOf(resultadoStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Valor de resultado inválido: " + resultadoStr);
            }
        }

        String accionStr2  = accion    != null ? accion.name()    : null;
        String resultadoStr2 = resultado != null ? resultado.name() : null;

        return logRepo.buscarConFiltros(idUsuario, fechaDesde, fechaHasta, accionStr2, recurso, idRecurso, resultadoStr2)
                .stream()
                .map(l -> new LogAuditoriaResponse(
                        l.getIdLog(),
                        l.getIdUsuario(),
                        l.getRolUsuario().name(),
                        l.getAccion().name(),
                        l.getRecurso(),
                        l.getIdRecurso(),
                        l.getFechaHora(),
                        l.getResultado().name()
                ))
                .collect(Collectors.toList());
    }
}
