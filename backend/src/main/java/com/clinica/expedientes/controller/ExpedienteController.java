package com.clinica.expedientes.controller;

import com.clinica.expedientes.dto.request.*;
import com.clinica.expedientes.dto.response.*;
import com.clinica.expedientes.service.AdministradorService;
import com.clinica.expedientes.service.TerapeutaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/expedientes")
public class ExpedienteController {

    private final TerapeutaService terapeutaService;
    private final AdministradorService administradorService;

    public ExpedienteController(TerapeutaService terapeutaService, AdministradorService administradorService) {
        this.terapeutaService = terapeutaService;
        this.administradorService = administradorService;
    }

    // CU-02: Acceder a expediente clínico (TERAPEUTA con ABAC)
    @GetMapping("/{idExpediente}")
    public ResponseEntity<ExpedienteDetalleResponse> getExpediente(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long idExpediente) {
        return ResponseEntity.ok(terapeutaService.getExpediente(userId, idExpediente));
    }

    // CU-03: Registrar reporte de sesión (TERAPEUTA con ABAC)
    @PostMapping("/{idExpediente}/reportes")
    public ResponseEntity<ReporteCreadoResponse> crearReporte(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long idExpediente,
            @Valid @RequestBody CrearReporteRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(terapeutaService.crearReporte(userId, idExpediente, req));
    }

    // CU-10: Registrar expediente clínico (ADMINISTRADOR)
    @PostMapping
    public ResponseEntity<ExpedienteCreadoResponse> crearExpediente(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody CrearExpedienteRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(administradorService.crearExpediente(userId, req));
    }

    // RNF-01: Cambiar estado del expediente (ADMINISTRADOR)
    @PatchMapping("/{idExpediente}/estado")
    public ResponseEntity<ExpedienteEstadoResponse> cambiarEstado(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long idExpediente,
            @Valid @RequestBody CambiarEstadoExpedienteRequest req) {
        return ResponseEntity.ok(administradorService.cambiarEstado(userId, idExpediente, req));
    }

    // CU-11: Registrar entrevista socioeconómica (ADMINISTRADOR)
    @PostMapping("/{idExpediente}/entrevista-socioeconomica")
    public ResponseEntity<EntrevistaResponse> registrarEntrevista(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long idExpediente,
            @Valid @RequestBody RegistrarEntrevistaRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(administradorService.registrarEntrevista(userId, idExpediente, req));
    }

    // CU-12: Registrar consentimiento informado (ADMINISTRADOR)
    @PostMapping("/{idExpediente}/consentimiento")
    public ResponseEntity<ConsentimientoResponse> registrarConsentimiento(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long idExpediente,
            @Valid @RequestBody RegistrarConsentimientoRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(administradorService.registrarConsentimiento(userId, idExpediente, req));
    }
}
