package com.clinica.expedientes.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class CrearExpedienteRequest {

    @NotNull
    private Long idPaciente;

    @NotNull
    private Long idTerapeuta;

    private LocalDateTime fechaProxCita;
}
