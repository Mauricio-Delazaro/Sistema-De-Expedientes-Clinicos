package com.clinica.expedientes.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class CrearReporteRequest {

    @NotNull
    private LocalDate fechaSesion;

    @Positive
    private Integer duracionSesion;

    @NotBlank
    private String observacionesClinicas;

    private String comentariosTerapeuta;
}
