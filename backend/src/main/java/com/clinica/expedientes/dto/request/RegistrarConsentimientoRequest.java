package com.clinica.expedientes.dto.request;

import jakarta.validation.constraints.NotBlank;

public class RegistrarConsentimientoRequest {

    @NotBlank(message = "El cuerpo del texto es obligatorio")
    private String cuerpoDelTexto;

    @NotBlank(message = "El acuerdo de confidencialidad es obligatorio")
    private String acuerdoConfidencial;

    public String getCuerpoDelTexto() { return cuerpoDelTexto; }
    public void setCuerpoDelTexto(String cuerpoDelTexto) { this.cuerpoDelTexto = cuerpoDelTexto; }

    public String getAcuerdoConfidencial() { return acuerdoConfidencial; }
    public void setAcuerdoConfidencial(String acuerdoConfidencial) { this.acuerdoConfidencial = acuerdoConfidencial; }
}
