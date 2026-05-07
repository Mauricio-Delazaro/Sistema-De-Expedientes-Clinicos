package com.clinica.expedientes.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "informe_consentimiento")
@PrimaryKeyJoinColumn(name = "id_documento")
@Getter @Setter @NoArgsConstructor
public class InformeConsentimiento extends Documento {

    @Column(name = "cuerpo_del_texto", nullable = false, columnDefinition = "TEXT")
    private String cuerpoDelTexto;

    @Column(name = "acuerdo_confidencial", nullable = false, columnDefinition = "TEXT")
    private String acuerdoConfidencial;
}
