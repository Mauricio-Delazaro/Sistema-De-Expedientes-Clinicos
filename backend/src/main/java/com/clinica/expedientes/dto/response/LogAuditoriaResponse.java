package com.clinica.expedientes.dto.response;

import java.time.OffsetDateTime;

public class LogAuditoriaResponse {
    private final Long idLog;
    private final Long idUsuario;
    private final String rolUsuario;
    private final String accion;
    private final String recurso;
    private final String idRecurso;
    private final OffsetDateTime fechaHora;
    private final String resultado;

    public LogAuditoriaResponse(Long idLog, Long idUsuario, String rolUsuario, String accion,
                                 String recurso, String idRecurso, OffsetDateTime fechaHora, String resultado) {
        this.idLog = idLog;
        this.idUsuario = idUsuario;
        this.rolUsuario = rolUsuario;
        this.accion = accion;
        this.recurso = recurso;
        this.idRecurso = idRecurso;
        this.fechaHora = fechaHora;
        this.resultado = resultado;
    }

    public Long getIdLog() { return idLog; }
    public Long getIdUsuario() { return idUsuario; }
    public String getRolUsuario() { return rolUsuario; }
    public String getAccion() { return accion; }
    public String getRecurso() { return recurso; }
    public String getIdRecurso() { return idRecurso; }
    public OffsetDateTime getFechaHora() { return fechaHora; }
    public String getResultado() { return resultado; }
}
