package com.clinica.expedientes.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "registro_auditoria")
@Getter @Setter @NoArgsConstructor
public class RegistroAuditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_log")
    private Long idLog;

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol_usuario", nullable = false)
    private RolUsuario rolUsuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccionAuditoria accion;

    @Column(nullable = false, length = 100)
    private String recurso;

    @Column(name = "id_recurso", nullable = false, length = 100)
    private String idRecurso;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResultadoAuditoria resultado;

    public enum RolUsuario { TERAPEUTA, SUPERVISOR, ADMINISTRADOR }

    public enum AccionAuditoria {
        CONSULTAR_EXPEDIENTE, MODIFICAR_EXPEDIENTE, CAMBIAR_ESTADO_EXPEDIENTE,
        REGISTRAR_ENTREVISTA, REGISTRAR_CONSENTIMIENTO,
        REGISTRAR_REPORTE, MODIFICAR_REPORTE, ENVIAR_REPORTE,
        APROBAR_REPORTE, RECHAZAR_REPORTE
    }

    public enum ResultadoAuditoria { PERMITIDO, DENEGADO }
}
