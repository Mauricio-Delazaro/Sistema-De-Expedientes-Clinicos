package com.clinica.expedientes.service;

import com.clinica.expedientes.model.RegistroAuditoria;
import com.clinica.expedientes.repository.AuditoriaRepository;
import com.clinica.expedientes.security.UsuarioAutenticado;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditoriaService {

    private final AuditoriaRepository auditoriaRepository;

    public void registrar(UsuarioAutenticado usuario,
                          RegistroAuditoria.AccionAuditoria accion,
                          String recurso,
                          String idRecurso,
                          RegistroAuditoria.ResultadoAuditoria resultado) {
        RegistroAuditoria log = new RegistroAuditoria();
        log.setIdUsuario(usuario.getIdUsuario());
        log.setRolUsuario(RegistroAuditoria.RolUsuario.valueOf(usuario.getRol()));
        log.setAccion(accion);
        log.setRecurso(recurso);
        log.setIdRecurso(idRecurso);
        log.setFechaHora(LocalDateTime.now());
        log.setResultado(resultado);
        auditoriaRepository.save(log);
    }

    public List<RegistroAuditoria> consultar(Long idUsuario,
                                              LocalDateTime fechaDesde,
                                              LocalDateTime fechaHasta,
                                              String accion,
                                              String recurso,
                                              String idRecurso,
                                              String resultado) {
        Specification<RegistroAuditoria> spec = Specification.where(null);

        if (idUsuario != null)
            spec = spec.and((r, q, cb) -> cb.equal(r.get("idUsuario"), idUsuario));
        if (fechaDesde != null)
            spec = spec.and((r, q, cb) -> cb.greaterThanOrEqualTo(r.get("fechaHora"), fechaDesde));
        if (fechaHasta != null)
            spec = spec.and((r, q, cb) -> cb.lessThanOrEqualTo(r.get("fechaHora"), fechaHasta));
        if (accion != null)
            spec = spec.and((r, q, cb) -> cb.equal(r.get("accion"),
                    RegistroAuditoria.AccionAuditoria.valueOf(accion)));
        if (recurso != null)
            spec = spec.and((r, q, cb) -> cb.equal(r.get("recurso"), recurso));
        if (idRecurso != null)
            spec = spec.and((r, q, cb) -> cb.equal(r.get("idRecurso"), idRecurso));
        if (resultado != null)
            spec = spec.and((r, q, cb) -> cb.equal(r.get("resultado"),
                    RegistroAuditoria.ResultadoAuditoria.valueOf(resultado)));

        return auditoriaRepository.findAll(spec);
    }
}
