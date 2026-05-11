package com.clinica.expedientes.dto.response;

import java.time.LocalDate;

public class ReportePendienteResponse {
    private final Long idReporte;
    private final Long idTerapeuta;
    private final String nombreTerapeuta;
    private final LocalDate fechaSesion;

    public ReportePendienteResponse(Long idReporte, Long idTerapeuta,
                                     String nombreTerapeuta, LocalDate fechaSesion) {
        this.idReporte = idReporte;
        this.idTerapeuta = idTerapeuta;
        this.nombreTerapeuta = nombreTerapeuta;
        this.fechaSesion = fechaSesion;
    }

    public Long getIdReporte() { return idReporte; }
    public Long getIdTerapeuta() { return idTerapeuta; }
    public String getNombreTerapeuta() { return nombreTerapeuta; }
    public LocalDate getFechaSesion() { return fechaSesion; }
}
