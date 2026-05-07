package com.clinica.expedientes.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuario_cache")
@Getter @Setter @NoArgsConstructor
public class UsuarioCache {

    @Id
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "nombre_completo", nullable = false, length = 100)
    private String nombreCompleto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUsuario tipo;

    @Column(name = "username", unique = true, length = 50)
    private String username;

    @Column(name = "password_hash", length = 255)
    private String passwordHash;

    public enum TipoUsuario {
        PACIENTE, TERAPEUTA, SUPERVISOR, ADMINISTRADOR
    }
}
