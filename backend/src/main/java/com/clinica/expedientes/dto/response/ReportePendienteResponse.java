package com.clinica.expedientes.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter @AllArgsConstructor
public class ReportePendienteResponse {
    private Long idReporte;
    private Long idTerapeuta;
    private String nombreTerapeuta;
    private LocalDate fechaSesion;
}
