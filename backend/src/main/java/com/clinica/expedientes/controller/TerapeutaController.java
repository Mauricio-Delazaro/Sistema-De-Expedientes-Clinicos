package com.clinica.expedientes.controller;

import com.clinica.expedientes.dto.request.CrearReporteRequest;
import com.clinica.expedientes.dto.response.ExpedienteResponse;
import com.clinica.expedientes.dto.response.PacienteAsignadoResponse;
import com.clinica.expedientes.dto.response.ReporteResponse;
import com.clinica.expedientes.security.UsuarioAutenticado;
import com.clinica.expedientes.service.TerapeutaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TerapeutaController {

    private final TerapeutaService terapeutaService;

    @GetMapping("/terapeutas/mis-pacientes")
    @PreAuthorize("hasRole('TERAPEUTA')")
    public ResponseEntity<List<PacienteAsignadoResponse>> misPacientes(
            @AuthenticationPrincipal UsuarioAutenticado terapeuta) {
        return ResponseEntity.ok(terapeutaService.listarPacientes(terapeuta));
    }

    @GetMapping("/expedientes/{idExpediente}")
    @PreAuthorize("hasRole('TERAPEUTA')")
    public ResponseEntity<ExpedienteResponse> verExpediente(
            @PathVariable Long idExpediente,
            @AuthenticationPrincipal UsuarioAutenticado terapeuta) {
        return ResponseEntity.ok(terapeutaService.verExpediente(idExpediente, terapeuta));
    }

    @PostMapping("/expedientes/{idExpediente}/reportes")
    @PreAuthorize("hasRole('TERAPEUTA')")
    public ResponseEntity<ReporteResponse> crearReporte(
            @PathVariable Long idExpediente,
            @Valid @RequestBody CrearReporteRequest req,
            @AuthenticationPrincipal UsuarioAutenticado terapeuta) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(terapeutaService.crearReporte(idExpediente, req, terapeuta));
    }

    @PatchMapping("/reportes/{idReporte}/enviar")
    @PreAuthorize("hasRole('TERAPEUTA')")
    public ResponseEntity<ReporteResponse> enviarReporte(
            @PathVariable Long idReporte,
            @AuthenticationPrincipal UsuarioAutenticado terapeuta) {
        return ResponseEntity.ok(terapeutaService.enviarReporte(idReporte, terapeuta));
    }

    @PutMapping("/reportes/{idReporte}")
    @PreAuthorize("hasRole('TERAPEUTA')")
    public ResponseEntity<ReporteResponse> modificarReporte(
            @PathVariable Long idReporte,
            @Valid @RequestBody CrearReporteRequest req,
            @AuthenticationPrincipal UsuarioAutenticado terapeuta) {
        return ResponseEntity.ok(terapeutaService.modificarReporte(idReporte, req, terapeuta));
    }
}
