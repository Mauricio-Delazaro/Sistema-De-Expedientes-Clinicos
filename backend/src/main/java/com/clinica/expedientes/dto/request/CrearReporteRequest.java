package com.clinica.expedientes.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public class CrearReporteRequest {

    @NotNull(message = "La fecha de sesión es obligatoria")
    private LocalDate fechaSesion;

    @Positive(message = "La duración de la sesión debe ser mayor a 0")
    private Integer duracionSesion;

    @NotBlank(message = "Las observaciones clínicas son obligatorias")
    private String observacionesClinicas;

    private String comentariosTerapeuta;

    public LocalDate getFechaSesion() { return fechaSesion; }
    public void setFechaSesion(LocalDate fechaSesion) { this.fechaSesion = fechaSesion; }

    public Integer getDuracionSesion() { return duracionSesion; }
    public void setDuracionSesion(Integer duracionSesion) { this.duracionSesion = duracionSesion; }

    public String getObservacionesClinicas() { return observacionesClinicas; }
    public void setObservacionesClinicas(String observacionesClinicas) { this.observacionesClinicas = observacionesClinicas; }

    public String getComentariosTerapeuta() { return comentariosTerapeuta; }
    public void setComentariosTerapeuta(String comentariosTerapeuta) { this.comentariosTerapeuta = comentariosTerapeuta; }
}
