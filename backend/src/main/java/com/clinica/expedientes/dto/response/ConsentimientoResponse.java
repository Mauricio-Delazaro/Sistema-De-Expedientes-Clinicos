package com.clinica.expedientes.dto.response;

import java.time.LocalDate;

public class ConsentimientoResponse {
    private final Long idDocumento;
    private final Long idExpediente;
    private final LocalDate fecha;
    private final String cuerpoDelTexto;
    private final String acuerdoConfidencial;

    public ConsentimientoResponse(Long idDocumento, Long idExpediente, LocalDate fecha,
                                   String cuerpoDelTexto, String acuerdoConfidencial) {
        this.idDocumento = idDocumento;
        this.idExpediente = idExpediente;
        this.fecha = fecha;
        this.cuerpoDelTexto = cuerpoDelTexto;
        this.acuerdoConfidencial = acuerdoConfidencial;
    }

    public Long getIdDocumento() { return idDocumento; }
    public Long getIdExpediente() { return idExpediente; }
    public LocalDate getFecha() { return fecha; }
    public String getCuerpoDelTexto() { return cuerpoDelTexto; }
    public String getAcuerdoConfidencial() { return acuerdoConfidencial; }
}
