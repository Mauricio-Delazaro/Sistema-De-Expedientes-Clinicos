package com.clinica.expedientes.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "entrevista_socioeconomica")
@PrimaryKeyJoinColumn(name = "id_documento")
@Getter @Setter @NoArgsConstructor
public class EntrevistaSocioeconomica extends Documento {

    @Column(name = "ingreso_familiar", nullable = false, precision = 15, scale = 2)
    private BigDecimal ingresoFamiliar;

    @Column(name = "gasto_alimentacion", nullable = false, precision = 15, scale = 2)
    private BigDecimal gastoAlimentacion;

    @Column(name = "lugar_procedencia", nullable = false, length = 100)
    private String lugarProcedencia;

    @Column(length = 1000)
    private String vivienda;

    @Column(name = "estado_salud_familiar", nullable = false, length = 50)
    private String estadoSaludFamiliar;
}
