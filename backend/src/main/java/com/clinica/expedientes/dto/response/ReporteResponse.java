package com.clinica.expedientes.dto.response;

import com.clinica.expedientes.model.ReporteSesion;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Builder
public class ReporteResponse {
    private Long idDocumento;
    private Long idExpediente;
    private Long idTerapeuta;
    private String nombreTerapeuta;
    private String nombrePaciente;
    private LocalDate fechaSesion;
    private Integer duracionSesion;
    private String observacionesClinicas;
    private String comentariosTerapeuta;
    private String comentariosSupervisor;
    private ReporteSesion.EstadoReporte estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
}
