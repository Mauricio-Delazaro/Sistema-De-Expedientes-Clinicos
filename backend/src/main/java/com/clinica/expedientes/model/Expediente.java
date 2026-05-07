package com.clinica.expedientes.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "expediente")
@Getter @Setter @NoArgsConstructor
public class Expediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_expediente")
    private Long idExpediente;

    @Column(name = "id_paciente", nullable = false, unique = true)
    private Long idPaciente;

    @Column(name = "id_terapeuta", nullable = false)
    private Long idTerapeuta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoExpediente estado = EstadoExpediente.ACTIVO;

    @Column(name = "fecha_prox_cita")
    private LocalDateTime fechaProxCita;

    @OneToMany(mappedBy = "expediente", fetch = FetchType.LAZY)
    private List<Documento> documentos;

    public enum EstadoExpediente {
        ACTIVO, ARCHIVADO
    }
}
