package com.clinica.expedientes.dto.response;

import com.clinica.expedientes.model.Expediente;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Builder
public class ExpedienteResponse {
    private Long idExpediente;
    private Expediente.EstadoExpediente estado;
    private LocalDateTime fechaProxCita;
    private PacienteInfo paciente;
    private DocumentoResumen entrevistaSocioeconomica;
    private DocumentoResumen informeConsentimiento;
    private List<ReporteResumen> reportesSesion;

    @Getter @Builder
    public static class PacienteInfo {
        private Long idPaciente;
        private String nombreCompleto;
    }

    @Getter @Builder
    public static class DocumentoResumen {
        private Long idDocumento;
        private java.time.LocalDate fecha;
    }

    @Getter @Builder
    public static class ReporteResumen {
        private Long idDocumento;
        private java.time.LocalDate fechaSesion;
        private com.clinica.expedientes.model.ReporteSesion.EstadoReporte estado;
    }
}
