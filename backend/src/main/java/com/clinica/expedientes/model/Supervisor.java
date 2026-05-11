package com.clinica.expedientes.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "supervisor")
@DiscriminatorValue("SUPERVISOR")
@PrimaryKeyJoinColumn(name = "id_supervisor")
public class Supervisor extends Usuario {

    @ManyToMany
    @JoinTable(
        name = "supervisor_terapeuta",
        joinColumns = @JoinColumn(name = "id_supervisor"),
        inverseJoinColumns = @JoinColumn(name = "id_terapeuta")
    )
    private List<Terapeuta> terapeutas;

    public List<Terapeuta> getTerapeutas() { return terapeutas; }
    public void setTerapeutas(List<Terapeuta> terapeutas) { this.terapeutas = terapeutas; }
}
