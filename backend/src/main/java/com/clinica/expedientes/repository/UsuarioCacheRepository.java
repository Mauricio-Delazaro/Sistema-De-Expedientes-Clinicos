package com.clinica.expedientes.repository;

import com.clinica.expedientes.model.UsuarioCache;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioCacheRepository extends JpaRepository<UsuarioCache, Long> {
    Optional<UsuarioCache> findByUsername(String username);
    List<UsuarioCache> findByTipo(UsuarioCache.TipoUsuario tipo);
}
