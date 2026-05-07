package com.clinica.expedientes.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ConsentimientoRequest {

    @NotBlank
    private String cuerpoDelTexto;

    @NotBlank
    private String acuerdoConfidencial;
}
