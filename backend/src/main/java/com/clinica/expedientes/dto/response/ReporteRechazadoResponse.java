package com.clinica.expedientes.dto.response;

import java.time.OffsetDateTime;

public class ReporteRechazadoResponse {
    private final Long idDocumento;
    private final String estado;
    private final String comentariosSupervisor;
    private final OffsetDateTime fechaModificacion;

    public ReporteRechazadoResponse(Long idDocumento, String estado,
                                     String comentariosSupervisor, OffsetDateTime fechaModificacion) {
        this.idDocumento = idDocumento;
        this.estado = estado;
        this.comentariosSupervisor = comentariosSupervisor;
        this.fechaModificacion = fechaModificacion;
    }

    public Long getIdDocumento() { return idDocumento; }
    public String getEstado() { return estado; }
    public String getComentariosSupervisor() { return comentariosSupervisor; }
    public OffsetDateTime getFechaModificacion() { return fechaModificacion; }
}
