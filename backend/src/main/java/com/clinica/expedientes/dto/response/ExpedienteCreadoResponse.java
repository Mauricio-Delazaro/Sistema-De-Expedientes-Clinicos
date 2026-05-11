package com.clinica.expedientes.dto.response;

import java.time.OffsetDateTime;

public class ExpedienteCreadoResponse {
    private final Long idExpediente;
    private final Long idPaciente;
    private final Long idTerapeuta;
    private final String estado;
    private final OffsetDateTime fechaProxCita;

    public ExpedienteCreadoResponse(Long idExpediente, Long idPaciente, Long idTerapeuta,
                                     String estado, OffsetDateTime fechaProxCita) {
        this.idExpediente = idExpediente;
        this.idPaciente = idPaciente;
        this.idTerapeuta = idTerapeuta;
        this.estado = estado;
        this.fechaProxCita = fechaProxCita;
    }

    public Long getIdExpediente() { return idExpediente; }
    public Long getIdPaciente() { return idPaciente; }
    public Long getIdTerapeuta() { return idTerapeuta; }
    public String getEstado() { return estado; }
    public OffsetDateTime getFechaProxCita() { return fechaProxCita; }
}
