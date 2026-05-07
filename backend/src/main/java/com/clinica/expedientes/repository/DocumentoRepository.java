package com.clinica.expedientes.repository;

import com.clinica.expedientes.model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {
}
