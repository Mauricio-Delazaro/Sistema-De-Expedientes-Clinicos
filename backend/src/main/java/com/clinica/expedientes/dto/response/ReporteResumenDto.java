package com.clinica.expedientes.dto.response;

import java.time.LocalDate;

public class ReporteResumenDto {
    private final Long idDocumento;
    private final LocalDate fechaSesion;
    private final String estado;

    public ReporteResumenDto(Long idDocumento, LocalDate fechaSesion, String estado) {
        this.idDocumento = idDocumento;
        this.fechaSesion = fechaSesion;
        this.estado = estado;
    }

    public Long getIdDocumento() { return idDocumento; }
    public LocalDate getFechaSesion() { return fechaSesion; }
    public String getEstado() { return estado; }
}
