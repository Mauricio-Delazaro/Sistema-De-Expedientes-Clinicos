package com.clinica.expedientes.dto.response;

import java.time.LocalDate;

public class PacienteDetalleDto {
    private final Long idPaciente;
    private final String nombreCompleto;
    private final Integer edad;
    private final LocalDate fechaNacimiento;
    private final String correoElectronico;
    private final String numeroTelefonico;

    public PacienteDetalleDto(Long idPaciente, String nombreCompleto, Integer edad,
                               LocalDate fechaNacimiento, String correoElectronico, String numeroTelefonico) {
        this.idPaciente = idPaciente;
        this.nombreCompleto = nombreCompleto;
        this.edad = edad;
        this.fechaNacimiento = fechaNacimiento;
        this.correoElectronico = correoElectronico;
        this.numeroTelefonico = numeroTelefonico;
    }

    public Long getIdPaciente() { return idPaciente; }
    public String getNombreCompleto() { return nombreCompleto; }
    public Integer getEdad() { return edad; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public String getCorreoElectronico() { return correoElectronico; }
    public String getNumeroTelefonico() { return numeroTelefonico; }
}
