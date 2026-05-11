package com.clinica.expedientes.repository;

import com.clinica.expedientes.model.Expediente;
import com.clinica.expedientes.model.InformeConsentimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InformeConsentimientoRepository extends JpaRepository<InformeConsentimiento, Long> {

    Optional<InformeConsentimiento> findByExpediente(Expediente expediente);

    boolean existsByExpediente(Expediente expediente);
}
