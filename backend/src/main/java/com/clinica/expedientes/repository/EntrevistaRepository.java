package com.clinica.expedientes.repository;

import com.clinica.expedientes.model.EntrevistaSocioeconomica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntrevistaRepository extends JpaRepository<EntrevistaSocioeconomica, Long> {
}
