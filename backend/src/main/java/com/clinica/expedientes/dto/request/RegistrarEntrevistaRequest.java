package com.clinica.expedientes.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class RegistrarEntrevistaRequest {

    @NotNull(message = "El ingreso familiar es obligatorio")
    @DecimalMin(value = "0.0", message = "El ingreso familiar no puede ser negativo")
    private BigDecimal ingresoFamiliar;

    @NotNull(message = "El gasto de alimentación es obligatorio")
    @DecimalMin(value = "0.0", message = "El gasto de alimentación no puede ser negativo")
    private BigDecimal gastoAlimentacion;

    @NotBlank(message = "El lugar de procedencia es obligatorio")
    @Size(max = 100, message = "El lugar de procedencia no puede superar 100 caracteres")
    private String lugarProcedencia;

    @Size(max = 1000, message = "La descripción de vivienda no puede superar 1000 caracteres")
    private String vivienda;

    @NotBlank(message = "El estado de salud familiar es obligatorio")
    @Size(max = 50, message = "El estado de salud familiar no puede superar 50 caracteres")
    private String estadoSaludFamiliar;

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
