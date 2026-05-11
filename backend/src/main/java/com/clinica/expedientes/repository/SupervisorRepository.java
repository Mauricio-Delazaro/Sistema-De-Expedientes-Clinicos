package com.clinica.expedientes.repository;

import com.clinica.expedientes.model.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SupervisorRepository extends JpaRepository<Supervisor, Long> {

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END " +
           "FROM Supervisor s JOIN s.terapeutas t " +
           "WHERE s.idUsuario = :idSupervisor AND t.idUsuario = :idTerapeuta")
    boolean supervisorSupervisa(@Param("idSupervisor") Long idSupervisor,
                                @Param("idTerapeuta") Long idTerapeuta);

    @Query("SELECT s FROM Supervisor s LEFT JOIN FETCH s.terapeutas WHERE s.idUsuario = :id")
    Optional<Supervisor> findWithTerapeutasById(@Param("id") Long id);
}
