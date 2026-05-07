package com.clinica.expedientes.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class PacienteAsignadoResponse {
    private Long idPaciente;
    private String nombreCompleto;
    private Long idExpediente;
}
