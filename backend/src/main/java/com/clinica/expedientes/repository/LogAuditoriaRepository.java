package com.clinica.expedientes.repository;

import com.clinica.expedientes.model.LogAuditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;

public interface LogAuditoriaRepository extends JpaRepository<LogAuditoria, Long> {

    @Query(value =
           "SELECT * FROM log_auditoria " +
           "WHERE (CAST(:idUsuario AS BIGINT) IS NULL OR id_usuario = :idUsuario) " +
           "AND (CAST(:fechaDesde AS TIMESTAMPTZ) IS NULL OR fecha_hora >= CAST(:fechaDesde AS TIMESTAMPTZ)) " +
           "AND (CAST(:fechaHasta AS TIMESTAMPTZ) IS NULL OR fecha_hora <= CAST(:fechaHasta AS TIMESTAMPTZ)) " +
           "AND (CAST(:accion AS TEXT) IS NULL OR accion = :accion) " +
           "AND (CAST(:recurso AS TEXT) IS NULL OR recurso = :recurso) " +
           "AND (CAST(:idRecurso AS TEXT) IS NULL OR id_recurso = :idRecurso) " +
           "AND (CAST(:resultado AS TEXT) IS NULL OR resultado = :resultado) " +
           "ORDER BY fecha_hora DESC",
           nativeQuery = true)
    List<LogAuditoria> buscarConFiltros(@Param("idUsuario") Long idUsuario,
                                        @Param("fechaDesde") OffsetDateTime fechaDesde,
                                        @Param("fechaHasta") OffsetDateTime fechaHasta,
                                        @Param("accion") String accion,
                                        @Param("recurso") String recurso,
                                        @Param("idRecurso") String idRecurso,
                                        @Param("resultado") String resultado);
}
