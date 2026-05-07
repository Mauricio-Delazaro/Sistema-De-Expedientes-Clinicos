package com.clinica.expedientes.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class ErrorResponse {
    private int codigo;
    private String error;
    private String mensaje;
}
