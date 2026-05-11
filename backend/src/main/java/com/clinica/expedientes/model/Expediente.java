package com.clinica.expedientes.model;

import com.clinica.expedientes.model.enums.EstadoExpediente;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "expediente")
public class Expediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_expediente")
    private Long idExpediente;

    @OneToOne
    @JoinColumn(name = "id_paciente", nullable = false, unique = true)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_terapeuta", nullable = false)
    private Terapeuta terapeuta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoExpediente estado = EstadoExpediente.ACTIVO;

    @Column(name = "fecha_prox_cita")
    private OffsetDateTime fechaProxCita;

    @OneToMany(mappedBy = "expediente", fetch = FetchType.LAZY)
    private List<ReporteSesion> reportesSesion;

    @OneToOne(mappedBy = "expediente", fetch = FetchType.LAZY)
    private InformeConsentimiento informeConsentimiento;

    @OneToOne(mappedBy = "expediente", fetch = FetchType.LAZY)
    private EntrevistaSocioeconomica entrevistaSocioeconomica;

    public Long getIdExpediente() { return idExpediente; }
    public void setIdExpediente(Long idExpediente) { this.idExpediente = idExpediente; }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }

    public Terapeuta getTerapeuta() { return terapeuta; }
    public void setTerapeuta(Terapeuta terapeuta) { this.terapeuta = terapeuta; }

    public EstadoExpediente getEstado() { return estado; }
    public void setEstado(EstadoExpediente estado) { this.estado = estado; }

    public OffsetDateTime getFechaProxCita() { return fechaProxCita; }
    public void setFechaProxCita(OffsetDateTime fechaProxCita) { this.fechaProxCita = fechaProxCita; }

    public List<ReporteSesion> getReportesSesion() { return reportesSesion; }
    public void setReportesSesion(List<ReporteSesion> reportesSesion) { this.reportesSesion = reportesSesion; }

    public InformeConsentimiento getInformeConsentimiento() { return informeConsentimiento; }
    public void setInformeConsentimiento(InformeConsentimiento informeConsentimiento) { this.informeConsentimiento = informeConsentimiento; }

    public EntrevistaSocioeconomica getEntrevistaSocioeconomica() { return entrevistaSocioeconomica; }
    public void setEntrevistaSocioeconomica(EntrevistaSocioeconomica entrevistaSocioeconomica) { this.entrevistaSocioeconomica = entrevistaSocioeconomica; }
}
