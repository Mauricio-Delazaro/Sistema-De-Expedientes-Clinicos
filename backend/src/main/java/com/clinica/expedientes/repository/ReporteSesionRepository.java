package com.clinica.expedientes.repository;

import com.clinica.expedientes.model.ReporteSesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReporteSesionRepository extends JpaRepository<ReporteSesion, Long> {

    @Query("""
        SELECT r FROM ReporteSesion r
        WHERE r.estado = 'PENDIENTE'
          AND r.idTerapeuta IN :idsTerapeuta
          AND (:idTerapeuta IS NULL OR r.idTerapeuta = :idTerapeuta)
          AND (:fechaDesde IS NULL OR r.fechaSesion >= :fechaDesde)
          AND (:fechaHasta IS NULL OR r.fechaSesion <= :fechaHasta)
    """)
    List<ReporteSesion> findPendientesPorSupervisor(
            @Param("idsTerapeuta") List<Long> idsTerapeuta,
            @Param("idTerapeuta") Long idTerapeuta,
            @Param("fechaDesde") LocalDate fechaDesde,
            @Param("fechaHasta") LocalDate fechaHasta
    );
}
