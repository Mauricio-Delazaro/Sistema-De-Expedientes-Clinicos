package com.clinica.expedientes.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "entrevista_socioeconomica")
public class EntrevistaSocioeconomica extends Documento {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_expediente", nullable = false, unique = true)
    private Expediente expediente;

    @Column(name = "ingreso_familiar", nullable = false, precision = 15, scale = 2)
    private BigDecimal ingresoFamiliar;

    @Column(name = "gasto_alimentacion", nullable = false, precision = 15, scale = 2)
    private BigDecimal gastoAlimentacion;

    @Column(name = "lugar_procedencia", nullable = false, length = 100)
    private String lugarProcedencia;

    @Column(name = "vivienda", length = 1000)
    private String vivienda;

    @Column(name = "estado_salud_familiar", nullable = false, length = 50)
    private String estadoSaludFamiliar;

    public Expediente getExpediente() { return expediente; }
    public void setExpediente(Expediente expediente) { this.expediente = expediente; }

    public BigDecimal getIngresoFamiliar() { return ingresoFamiliar; }
    public void setIngresoFamiliar(BigDecimal ingresoFamiliar) { this.ingresoFamiliar = ingresoFamiliar; }

    public BigDecimal getGastoAlimentacion() { return gastoAlimentacion; }
    public void setGastoAlimentacion(BigDecimal gastoAlimentacion) { this.gastoAlimentacion = gastoAlimentacion; }

    public String getLugarProcedencia() { return lugarProcedencia; }
    public void setLugarProcedencia(String lugarProcedencia) { this.lugarProcedencia = lugarProcedencia; }

    public String getVivienda() { return vivienda; }
    public void setVivienda(String vivienda) { this.vivienda = vivienda; }

    public String getEstadoSaludFamiliar() { return estadoSaludFamiliar; }
    public void setEstadoSaludFamiliar(String estadoSaludFamiliar) { this.estadoSaludFamiliar = estadoSaludFamiliar; }
}
