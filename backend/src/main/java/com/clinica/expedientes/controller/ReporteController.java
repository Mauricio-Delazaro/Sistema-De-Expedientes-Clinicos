package com.clinica.expedientes.controller;

import com.clinica.expedientes.dto.request.CrearReporteRequest;
import com.clinica.expedientes.dto.request.RechazarReporteRequest;
import com.clinica.expedientes.dto.response.*;
import com.clinica.expedientes.service.SupervisorService;
import com.clinica.expedientes.service.TerapeutaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reportes")
public class ReporteController {

    private final TerapeutaService terapeutaService;
    private final SupervisorService supervisorService;

    public ReporteController(TerapeutaService terapeutaService, SupervisorService supervisorService) {
        this.terapeutaService = terapeutaService;
        this.supervisorService = supervisorService;
    }

    // CU-07: Visualizar contenido de reporte (TERAPEUTA propietario o SUPERVISOR que supervisa)
    @GetMapping("/{idReporte}")
    public ResponseEntity<ReporteDetalleResponse> getReporte(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long idReporte) {
        return ResponseEntity.ok(supervisorService.getReporte(userId, idReporte));
    }

    // CU-04: Enviar reporte a revisión (TERAPEUTA propietario)
    @PatchMapping("/{idReporte}/enviar")
    public ResponseEntity<ReporteEstadoResponse> enviarReporte(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long idReporte) {
        return ResponseEntity.ok(terapeutaService.enviarReporte(userId, idReporte));
    }

    // CU-05: Modificar reporte rechazado (TERAPEUTA propietario)
    @PutMapping("/{idReporte}")
    public ResponseEntity<ReporteModificadoResponse> modificarReporte(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long idReporte,
            @Valid @RequestBody CrearReporteRequest req) {
        return ResponseEntity.ok(terapeutaService.modificarReporte(userId, idReporte, req));
    }

    // CU-08: Aprobar reporte (SUPERVISOR)
    @PatchMapping("/{idReporte}/aprobar")
    public ResponseEntity<ReporteEstadoResponse> aprobarReporte(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long idReporte) {
        return ResponseEntity.ok(supervisorService.aprobarReporte(userId, idReporte));
    }

    // CU-09: Rechazar reporte (SUPERVISOR)
    @PatchMapping("/{idReporte}/rechazar")
    public ResponseEntity<ReporteRechazadoResponse> rechazarReporte(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long idReporte,
            @Valid @RequestBody RechazarReporteRequest req) {
        return ResponseEntity.ok(supervisorService.rechazarReporte(userId, idReporte, req));
    }
}
