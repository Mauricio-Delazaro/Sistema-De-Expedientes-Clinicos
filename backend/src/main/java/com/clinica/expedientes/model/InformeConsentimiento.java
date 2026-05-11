package com.clinica.expedientes.model;

import jakarta.persistence.*;

@Entity
@Table(name = "informe_consentimiento")
public class InformeConsentimiento extends Documento {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_expediente", nullable = false, unique = true)
    private Expediente expediente;

    @Column(name = "cuerpo_del_texto", nullable = false, columnDefinition = "TEXT")
    private String cuerpoDelTexto;

    @Column(name = "acuerdo_confidencial", nullable = false, columnDefinition = "TEXT")
    private String acuerdoConfidencial;

    public Expediente getExpediente() { return expediente; }
    public void setExpediente(Expediente expediente) { this.expediente = expediente; }

    public String getCuerpoDelTexto() { return cuerpoDelTexto; }
    public void setCuerpoDelTexto(String cuerpoDelTexto) { this.cuerpoDelTexto = cuerpoDelTexto; }

    public String getAcuerdoConfidencial() { return acuerdoConfidencial; }
    public void setAcuerdoConfidencial(String acuerdoConfidencial) { this.acuerdoConfidencial = acuerdoConfidencial; }
}
