package com.clinica.expedientes.dto.response;

import java.time.LocalDate;

public class DocumentoResumenDto {
    private final Long idDocumento;
    private final LocalDate fecha;

    public DocumentoResumenDto(Long idDocumento, LocalDate fecha) {
        this.idDocumento = idDocumento;
        this.fecha = fecha;
    }

    public Long getIdDocumento() { return idDocumento; }
    public LocalDate getFecha() { return fecha; }
}
