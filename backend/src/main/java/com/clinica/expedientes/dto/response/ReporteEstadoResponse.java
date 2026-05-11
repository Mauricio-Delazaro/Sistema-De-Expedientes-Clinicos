package com.clinica.expedientes.dto.response;

import java.time.OffsetDateTime;

public class ReporteEstadoResponse {
    private final Long idDocumento;
    private final String estado;
    private final OffsetDateTime fechaModificacion;

    public ReporteEstadoResponse(Long idDocumento, String estado, OffsetDateTime fechaModificacion) {
        this.idDocumento = idDocumento;
        this.estado = estado;
        this.fechaModificacion = fechaModificacion;
    }

    public Long getIdDocumento() { return idDocumento; }
    public String getEstado() { return estado; }
    public OffsetDateTime getFechaModificacion() { return fechaModificacion; }
}
