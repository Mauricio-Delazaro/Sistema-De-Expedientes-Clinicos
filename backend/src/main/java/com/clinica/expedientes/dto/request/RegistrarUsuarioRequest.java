package com.clinica.expedientes.dto.request;

import com.clinica.expedientes.model.UsuarioCache;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegistrarUsuarioRequest {

    @NotNull
    private Long idUsuario;

    @NotBlank @Size(max = 100)
    private String nombreCompleto;

    @NotNull
    private UsuarioCache.TipoUsuario tipo;
}
