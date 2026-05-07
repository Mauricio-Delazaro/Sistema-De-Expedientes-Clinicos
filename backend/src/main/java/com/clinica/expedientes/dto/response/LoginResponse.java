package com.clinica.expedientes.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private Long idUsuario;
    private String nombre;
    private String rol;
}
