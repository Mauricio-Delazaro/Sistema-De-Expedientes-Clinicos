package com.clinica.expedientes.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reporte_sesion")
@PrimaryKeyJoinColumn(name = "id_documento")
@Getter @Setter @NoArgsConstructor
public class ReporteSesion extends Documento {

    @Column(name = "id_terapeuta", nullable = false)
    private Long idTerapeuta;

    @Column(name = "fecha_sesion", nullable = false)
    private LocalDate fechaSesion;

    @Column(name = "duracion_sesion")
    private Integer duracionSesion;

    @Column(name = "observaciones_clinicas", nullable = false, columnDefinition = "TEXT")
    private String observacionesClinicas;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoReporte estado = EstadoReporte.CREADO;

    @Column(name = "comentarios_terapeuta", columnDefinition = "TEXT")
    private String comentariosTerapeuta;

    @Column(name = "comentarios_supervisor", columnDefinition = "TEXT")
    private String comentariosSupervisor;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_modificacion", nullable = false)
    private LocalDateTime fechaModificacion;

    public enum EstadoReporte {
        CREADO, PENDIENTE, APROBADO, RECHAZADO
    }
}
