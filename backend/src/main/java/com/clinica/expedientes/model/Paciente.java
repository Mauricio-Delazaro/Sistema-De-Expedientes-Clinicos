package com.clinica.expedientes.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "paciente")
@DiscriminatorValue("PACIENTE")
@PrimaryKeyJoinColumn(name = "id_paciente")
public class Paciente extends Usuario {

    @Column(nullable = false)
    private Integer edad;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "correo_electronico", unique = true, length = 100)
    private String correoElectronico;

    @Column(name = "numero_telefonico", nullable = false, unique = true, length = 20)
    private String numeroTelefonico;

    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public String getNumeroTelefonico() { return numeroTelefonico; }
    public void setNumeroTelefonico(String numeroTelefonico) { this.numeroTelefonico = numeroTelefonico; }
}
