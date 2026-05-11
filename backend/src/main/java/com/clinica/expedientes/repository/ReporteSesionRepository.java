package com.clinica.expedientes.repository;

import com.clinica.expedientes.model.ReporteSesion;
import com.clinica.expedientes.model.enums.EstadoReporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReporteSesionRepository extends JpaRepository<ReporteSesion, Long> {

    // Reportes pendientes de los terapeutas supervisados por el supervisor dado
    @Query("SELECT r FROM ReporteSesion r JOIN r.terapeuta t " +
           "WHERE r.estado = :estado " +
           "AND EXISTS (SELECT s FROM Supervisor s WHERE s.idUsuario = :idSupervisor AND t MEMBER OF s.terapeutas) " +
           "AND (:idTerapeuta IS NULL OR t.idUsuario = :idTerapeuta) " +
           "AND (:fechaDesde IS NULL OR r.fechaSesion >= :fechaDesde) " +
           "AND (:fechaHasta IS NULL OR r.fechaSesion <= :fechaHasta) " +
           "ORDER BY r.fechaSesion DESC")
    List<ReporteSesion> findByEstadoAndSupervisor(@Param("estado") EstadoReporte estado,
                                                   @Param("idSupervisor") Long idSupervisor,
                                                   @Param("idTerapeuta") Long idTerapeuta,
                                                   @Param("fechaDesde") LocalDate fechaDesde,
                                                   @Param("fechaHasta") LocalDate fechaHasta);

    // Verifica que el supervisor supervisa al terapeuta del reporte
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
           "FROM ReporteSesion r JOIN r.terapeuta t " +
           "WHERE r.idDocumento = :idReporte " +
           "AND EXISTS (SELECT s FROM Supervisor s WHERE s.idUsuario = :idSupervisor AND t MEMBER OF s.terapeutas)")
    boolean supervisorTieneAccesoAReporte(@Param("idReporte") Long idReporte,
                                           @Param("idSupervisor") Long idSupervisor);
}
