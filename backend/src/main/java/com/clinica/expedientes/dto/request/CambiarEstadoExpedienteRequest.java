package com.clinica.expedientes.dto.request;

import com.clinica.expedientes.model.Expediente;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CambiarEstadoExpedienteRequest {

    @NotNull
    private Expediente.EstadoExpediente estado;
}
