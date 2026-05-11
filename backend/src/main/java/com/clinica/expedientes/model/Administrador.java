package com.clinica.expedientes.model;

import jakarta.persistence.*;

@Entity
@Table(name = "administrador")
@DiscriminatorValue("ADMINISTRADOR")
@PrimaryKeyJoinColumn(name = "id_administrador")
public class Administrador extends Usuario {
}
