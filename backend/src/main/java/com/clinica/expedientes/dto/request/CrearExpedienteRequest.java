package com.clinica.expedientes.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;

public class CrearExpedienteRequest {

    @NotNull(message = "El id del paciente es obligatorio")
    private Long idPaciente;

    @NotNull(message = "El id del terapeuta es obligatorio")
    private Long idTerapeuta;

    private OffsetDateTime fechaProxCita;

    public Long getIdPaciente() { return idPaciente; }
    public void setIdPaciente(Long idPaciente) { this.idPaciente = idPaciente; }

    public Long getIdTerapeuta() { return idTerapeuta; }
    public void setIdTerapeuta(Long idTerapeuta) { this.idTerapeuta = idTerapeuta; }

    public OffsetDateTime getFechaProxCita() { return fechaProxCita; }
    public void setFechaProxCita(OffsetDateTime fechaProxCita) { this.fechaProxCita = fechaProxCita; }
}
