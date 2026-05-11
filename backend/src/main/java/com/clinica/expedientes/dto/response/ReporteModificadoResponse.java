package com.clinica.expedientes.dto.response;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class ReporteModificadoResponse {
    private final Long idDocumento;
    private final LocalDate fechaSesion;
    private final Integer duracionSesion;
    private final String observacionesClinicas;
    private final String comentariosTerapeuta;
    private final String comentariosSupervisor;
    private final String estado;
    private final OffsetDateTime fechaModificacion;

    public ReporteModificadoResponse(Long idDocumento, LocalDate fechaSesion, Integer duracionSesion,
                                      String observacionesClinicas, String comentariosTerapeuta,
                                      String comentariosSupervisor, String estado, OffsetDateTime fechaModificacion) {
        this.idDocumento = idDocumento;
        this.fechaSesion = fechaSesion;
        this.duracionSesion = duracionSesion;
        this.observacionesClinicas = observacionesClinicas;
        this.comentariosTerapeuta = comentariosTerapeuta;
        this.comentariosSupervisor = comentariosSupervisor;
        this.estado = estado;
        this.fechaModificacion = fechaModificacion;
    }

    public Long getIdDocumento() { return idDocumento; }
    public LocalDate getFechaSesion() { return fechaSesion; }
    public Integer getDuracionSesion() { return duracionSesion; }
    public String getObservacionesClinicas() { return observacionesClinicas; }
    public String getComentariosTerapeuta() { return comentariosTerapeuta; }
    public String getComentariosSupervisor() { return comentariosSupervisor; }
    public String getEstado() { return estado; }
    public OffsetDateTime getFechaModificacion() { return fechaModificacion; }
}
