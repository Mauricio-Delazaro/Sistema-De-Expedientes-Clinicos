package com.clinica.expedientes.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "terapeuta")
@DiscriminatorValue("TERAPEUTA")
@PrimaryKeyJoinColumn(name = "id_terapeuta")
public class Terapeuta extends Usuario {

    @ManyToMany
    @JoinTable(
        name = "terapeuta_paciente",
        joinColumns = @JoinColumn(name = "id_terapeuta"),
        inverseJoinColumns = @JoinColumn(name = "id_paciente")
    )
    private List<Paciente> pacientes;

    public List<Paciente> getPacientes() { return pacientes; }
    public void setPacientes(List<Paciente> pacientes) { this.pacientes = pacientes; }
}
