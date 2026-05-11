package com.clinica.expedientes.dto.response;

import java.time.OffsetDateTime;
import java.util.List;

public class ExpedienteDetalleResponse {
    private final Long idExpediente;
    private final String estado;
    private final OffsetDateTime fechaProxCita;
    private final PacienteDetalleDto paciente;
    private final DocumentoResumenDto entrevistaSocioeconomica;
    private final DocumentoResumenDto informeConsentimiento;
    private final List<ReporteResumenDto> reportesSesion;

    public ExpedienteDetalleResponse(Long idExpediente, String estado, OffsetDateTime fechaProxCita,
                                      PacienteDetalleDto paciente, DocumentoResumenDto entrevistaSocioeconomica,
                                      DocumentoResumenDto informeConsentimiento, List<ReporteResumenDto> reportesSesion) {
        this.idExpediente = idExpediente;
        this.estado = estado;
        this.fechaProxCita = fechaProxCita;
        this.paciente = paciente;
        this.entrevistaSocioeconomica = entrevistaSocioeconomica;
        this.informeConsentimiento = informeConsentimiento;
        this.reportesSesion = reportesSesion;
    }

    public Long getIdExpediente() { return idExpediente; }
    public String getEstado() { return estado; }
    public OffsetDateTime getFechaProxCita() { return fechaProxCita; }
    public PacienteDetalleDto getPaciente() { return paciente; }
    public DocumentoResumenDto getEntrevistaSocioeconomica() { return entrevistaSocioeconomica; }
    public DocumentoResumenDto getInformeConsentimiento() { return informeConsentimiento; }
    public List<ReporteResumenDto> getReportesSesion() { return reportesSesion; }
}
