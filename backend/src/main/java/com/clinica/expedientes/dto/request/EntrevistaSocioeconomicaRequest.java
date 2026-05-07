package com.clinica.expedientes.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class EntrevistaSocioeconomicaRequest {

    @NotNull @DecimalMin("0.0")
    private BigDecimal ingresoFamiliar;

    @NotNull @DecimalMin("0.0")
    private BigDecimal gastoAlimentacion;

    @NotBlank @Size(max = 100)
    private String lugarProcedencia;

    @Size(max = 1000)
    private String vivienda;

    @NotBlank @Size(max = 50)
    private String estadoSaludFamiliar;
}
