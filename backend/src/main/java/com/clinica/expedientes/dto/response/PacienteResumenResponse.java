package com.clinica.expedientes.dto.response;

public class PacienteResumenResponse {
    private final Long idPaciente;
    private final String nombreCompleto;
    private final Long idExpediente;

    public PacienteResumenResponse(Long idPaciente, String nombreCompleto, Long idExpediente) {
        this.idPaciente = idPaciente;
        this.nombreCompleto = nombreCompleto;
        this.idExpediente = idExpediente;
    }

    public Long getIdPaciente() { return idPaciente; }
    public String getNombreCompleto() { return nombreCompleto; }
    public Long getIdExpediente() { return idExpediente; }
}
