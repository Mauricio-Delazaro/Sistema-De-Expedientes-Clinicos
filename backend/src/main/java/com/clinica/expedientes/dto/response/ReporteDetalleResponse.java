package com.clinica.expedientes.dto.response;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class ReporteDetalleResponse {
    private final Long idDocumento;
    private final Long idExpediente;
    private final Long idTerapeuta;
    private final String nombreTerapeuta;
    private final String nombrePaciente;
    private final LocalDate fechaSesion;
    private final Integer duracionSesion;
    private final String observacionesClinicas;
    private final String comentariosTerapeuta;
    private final String comentariosSupervisor;
    private final String estado;
    private final OffsetDateTime fechaCreacion;
    private final OffsetDateTime fechaModificacion;

    public ReporteDetalleResponse(Long idDocumento, Long idExpediente, Long idTerapeuta,
                                   String nombreTerapeuta, String nombrePaciente, LocalDate fechaSesion,
                                   Integer duracionSesion, String observacionesClinicas,
                                   String comentariosTerapeuta, String comentariosSupervisor,
                                   String estado, OffsetDateTime fechaCreacion, OffsetDateTime fechaModificacion) {
        this.idDocumento = idDocumento;
        this.idExpediente = idExpediente;
        this.idTerapeuta = idTerapeuta;
        this.nombreTerapeuta = nombreTerapeuta;
        this.nombrePaciente = nombrePaciente;
        this.fechaSesion = fechaSesion;
        this.duracionSesion = duracionSesion;
        this.observacionesClinicas = observacionesClinicas;
        this.comentariosTerapeuta = comentariosTerapeuta;
        this.comentariosSupervisor = comentariosSupervisor;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
    }

    public Long getIdDocumento() { return idDocumento; }
    public Long getIdExpediente() { return idExpediente; }
    public Long getIdTerapeuta() { return idTerapeuta; }
    public String getNombreTerapeuta() { return nombreTerapeuta; }
    public String getNombrePaciente() { return nombrePaciente; }
    public LocalDate getFechaSesion() { return fechaSesion; }
    public Integer getDuracionSesion() { return duracionSesion; }
    public String getObservacionesClinicas() { return observacionesClinicas; }
    public String getComentariosTerapeuta() { return comentariosTerapeuta; }
    public String getComentariosSupervisor() { return comentariosSupervisor; }
    public String getEstado() { return estado; }
    public OffsetDateTime getFechaCreacion() { return fechaCreacion; }
    public OffsetDateTime getFechaModificacion() { return fechaModificacion; }
}
