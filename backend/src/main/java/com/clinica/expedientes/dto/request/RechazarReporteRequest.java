package com.clinica.expedientes.dto.request;

import jakarta.validation.constraints.NotBlank;

public class RechazarReporteRequest {

    @NotBlank(message = "Los comentarios del supervisor son obligatorios al rechazar un reporte")
    private String comentariosSupervisor;

    public String getComentariosSupervisor() { return comentariosSupervisor; }
    public void setComentariosSupervisor(String comentariosSupervisor) { this.comentariosSupervisor = comentariosSupervisor; }
}
