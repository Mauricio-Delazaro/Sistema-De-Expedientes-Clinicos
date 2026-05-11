package com.clinica.expedientes.repository;

import com.clinica.expedientes.model.Expediente;
import com.clinica.expedientes.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExpedienteRepository extends JpaRepository<Expediente, Long> {

    boolean existsByPaciente(Paciente paciente);

    Optional<Expediente> findByPaciente(Paciente paciente);

    // Expedientes asignados directamente al terapeuta
    @Query("SELECT e FROM Expediente e JOIN FETCH e.paciente WHERE e.terapeuta.idUsuario = :idTerapeuta")
    List<Expediente> findByTerapeutaPaciente(@Param("idTerapeuta") Long idTerapeuta);

    // ABAC: verifica que el expediente esté asignado al terapeuta
    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END " +
           "FROM Expediente e " +
           "WHERE e.idExpediente = :idExpediente AND e.terapeuta.idUsuario = :idTerapeuta")
    boolean terapeutaTieneAccesoAExpediente(@Param("idExpediente") Long idExpediente,
                                             @Param("idTerapeuta") Long idTerapeuta);
}
