package com.clinica.expedientes.dto.response;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class ReporteCreadoResponse {
    private final Long idDocumento;
    private final Long idExpediente;
    private final LocalDate fechaSesion;
    private final Integer duracionSesion;
    private final String observacionesClinicas;
    private final String comentariosTerapeuta;
    private final String estado;
    private final OffsetDateTime fechaCreacion;
    private final OffsetDateTime fechaModificacion;

    public ReporteCreadoResponse(Long idDocumento, Long idExpediente, LocalDate fechaSesion,
                                  Integer duracionSesion, String observacionesClinicas,
                                  String comentariosTerapeuta, String estado,
                                  OffsetDateTime fechaCreacion, OffsetDateTime fechaModificacion) {
        this.idDocumento = idDocumento;
        this.idExpediente = idExpediente;
        this.fechaSesion = fechaSesion;
        this.duracionSesion = duracionSesion;
        this.observacionesClinicas = observacionesClinicas;
        this.comentariosTerapeuta = comentariosTerapeuta;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
    }

    public Long getIdDocumento() { return idDocumento; }
    public Long getIdExpediente() { return idExpediente; }
    public LocalDate getFechaSesion() { return fechaSesion; }
    public Integer getDuracionSesion() { return duracionSesion; }
    public String getObservacionesClinicas() { return observacionesClinicas; }
    public String getComentariosTerapeuta() { return comentariosTerapeuta; }
    public String getEstado() { return estado; }
    public OffsetDateTime getFechaCreacion() { return fechaCreacion; }
    public OffsetDateTime getFechaModificacion() { return fechaModificacion; }
}
