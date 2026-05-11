package com.clinica.expedientes.dto.response;

public class ExpedienteEstadoResponse {
    private final Long idExpediente;
    private final String estado;

    public ExpedienteEstadoResponse(Long idExpediente, String estado) {
        this.idExpediente = idExpediente;
        this.estado = estado;
    }

    public Long getIdExpediente() { return idExpediente; }
    public String getEstado() { return estado; }
}
