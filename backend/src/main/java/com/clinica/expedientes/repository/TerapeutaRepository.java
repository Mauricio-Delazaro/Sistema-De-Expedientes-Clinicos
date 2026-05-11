package com.clinica.expedientes.repository;

import com.clinica.expedientes.model.Terapeuta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TerapeutaRepository extends JpaRepository<Terapeuta, Long> {

    @Query("SELECT t FROM Terapeuta t LEFT JOIN FETCH t.pacientes WHERE t.idUsuario = :id")
    Optional<Terapeuta> findWithPacientesById(@Param("id") Long id);
}
