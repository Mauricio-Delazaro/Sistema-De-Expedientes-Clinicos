package com.clinica.expedientes.dto.request;

import jakarta.validation.constraints.NotBlank;

public class CambiarEstadoExpedienteRequest {

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
