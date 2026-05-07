package com.clinica.expedientes.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RechazarReporteRequest {

    @NotBlank
    private String comentariosSupervisor;
}
