package com.clinica.expedientes.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "supervisor_terapeuta")
@IdClass(SupervisorTerapeuta.PK.class)
@Getter @Setter @NoArgsConstructor
public class SupervisorTerapeuta {

    @Id
    @Column(name = "id_supervisor")
    private Long idSupervisor;

    @Id
    @Column(name = "id_terapeuta")
    private Long idTerapeuta;

    @Getter @Setter @NoArgsConstructor
    public static class PK implements Serializable {
        private Long idSupervisor;
        private Long idTerapeuta;
    }
}
