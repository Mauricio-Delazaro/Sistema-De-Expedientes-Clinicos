package com.clinica.expedientes.repository;

import com.clinica.expedientes.model.Expediente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExpedienteRepository extends JpaRepository<Expediente, Long> {

    List<Expediente> findByIdTerapeuta(Long idTerapeuta);

    boolean existsByIdPaciente(Long idPaciente);

    Optional<Expediente> findByIdPaciente(Long idPaciente);
}
