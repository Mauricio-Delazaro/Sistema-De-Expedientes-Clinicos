package com.clinica.expedientes.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EntrevistaResponse {
    private final Long idDocumento;
    private final Long idExpediente;
    private final LocalDate fecha;
    private final BigDecimal ingresoFamiliar;
    private final BigDecimal gastoAlimentacion;
    private final String lugarProcedencia;
    private final String vivienda;
    private final String estadoSaludFamiliar;

    public EntrevistaResponse(Long idDocumento, Long idExpediente, LocalDate fecha,
                               BigDecimal ingresoFamiliar, BigDecimal gastoAlimentacion,
                               String lugarProcedencia, String vivienda, String estadoSaludFamiliar) {
        this.idDocumento = idDocumento;
        this.idExpediente = idExpediente;
        this.fecha = fecha;
        this.ingresoFamiliar = ingresoFamiliar;
        this.gastoAlimentacion = gastoAlimentacion;
        this.lugarProcedencia = lugarProcedencia;
        this.vivienda = vivienda;
        this.estadoSaludFamiliar = estadoSaludFamiliar;
    }

    public Long getIdDocumento() { return idDocumento; }
    public Long getIdExpediente() { return idExpediente; }
    public LocalDate getFecha() { return fecha; }
    public BigDecimal getIngresoFamiliar() { return ingresoFamiliar; }
    public BigDecimal getGastoAlimentacion() { return gastoAlimentacion; }
    public String getLugarProcedencia() { return lugarProcedencia; }
    public String getVivienda() { return vivienda; }
    public String getEstadoSaludFamiliar() { return estadoSaludFamiliar; }
}
