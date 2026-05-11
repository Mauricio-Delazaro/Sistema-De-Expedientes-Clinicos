package com.clinica.expedientes.model;

import com.clinica.expedientes.model.enums.EstadoReporte;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "reporte_sesion")
public class ReporteSesion extends Documento {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_expediente", nullable = false)
    private Expediente expediente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_terapeuta", nullable = false)
    private Terapeuta terapeuta;

    @Column(name = "fecha_sesion", nullable = false)
    private LocalDate fechaSesion;

    @Column(name = "duracion_sesion")
    private Integer duracionSesion;

    @Column(name = "observaciones_clinicas", nullable = false, columnDefinition = "TEXT")
    private String observacionesClinicas;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoReporte estado = EstadoReporte.CREADO;

    @Column(name = "comentarios_terapeuta", columnDefinition = "TEXT")
    private String comentariosTerapeuta;

    @Column(name = "comentarios_supervisor", columnDefinition = "TEXT")
    private String comentariosSupervisor;

    @Column(name = "fecha_creacion", nullable = false)
    private OffsetDateTime fechaCreacion;

    @Column(name = "fecha_modificacion", nullable = false)
    private OffsetDateTime fechaModificacion;

    public Expediente getExpediente() { return expediente; }
    public void setExpediente(Expediente expediente) { this.expediente = expediente; }

    public Terapeuta getTerapeuta() { return terapeuta; }
    public void setTerapeuta(Terapeuta terapeuta) { this.terapeuta = terapeuta; }

    public LocalDate getFechaSesion() { return fechaSesion; }
    public void setFechaSesion(LocalDate fechaSesion) { this.fechaSesion = fechaSesion; }

    public Integer getDuracionSesion() { return duracionSesion; }
    public void setDuracionSesion(Integer duracionSesion) { this.duracionSesion = duracionSesion; }

    public String getObservacionesClinicas() { return observacionesClinicas; }
    public void setObservacionesClinicas(String observacionesClinicas) { this.observacionesClinicas = observacionesClinicas; }

    public EstadoReporte getEstado() { return estado; }
    public void setEstado(EstadoReporte estado) { this.estado = estado; }

    public String getComentariosTerapeuta() { return comentariosTerapeuta; }
    public void setComentariosTerapeuta(String comentariosTerapeuta) { this.comentariosTerapeuta = comentariosTerapeuta; }

    public String getComentariosSupervisor() { return comentariosSupervisor; }
    public void setComentariosSupervisor(String comentariosSupervisor) { this.comentariosSupervisor = comentariosSupervisor; }

    public OffsetDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(OffsetDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public OffsetDateTime getFechaModificacion() { return fechaModificacion; }
    public void setFechaModificacion(OffsetDateTime fechaModificacion) { this.fechaModificacion = fechaModificacion; }
}
