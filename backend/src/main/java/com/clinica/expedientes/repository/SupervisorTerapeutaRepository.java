package com.clinica.expedientes.repository;

import com.clinica.expedientes.model.SupervisorTerapeuta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupervisorTerapeutaRepository extends JpaRepository<SupervisorTerapeuta, SupervisorTerapeuta.PK> {

    boolean existsByIdSupervisorAndIdTerapeuta(Long idSupervisor, Long idTerapeuta);

    List<SupervisorTerapeuta> findByIdSupervisor(Long idSupervisor);
}
