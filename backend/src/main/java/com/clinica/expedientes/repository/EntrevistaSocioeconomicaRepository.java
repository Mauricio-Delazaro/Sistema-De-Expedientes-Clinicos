package com.clinica.expedientes.repository;

import com.clinica.expedientes.model.EntrevistaSocioeconomica;
import com.clinica.expedientes.model.Expediente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EntrevistaSocioeconomicaRepository extends JpaRepository<EntrevistaSocioeconomica, Long> {

    Optional<EntrevistaSocioeconomica> findByExpediente(Expediente expediente);

    boolean existsByExpediente(Expediente expediente);
}
