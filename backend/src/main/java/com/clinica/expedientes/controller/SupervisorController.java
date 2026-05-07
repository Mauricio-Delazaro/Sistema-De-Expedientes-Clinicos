package com.clinica.expedientes.controller;

import com.clinica.expedientes.dto.request.RechazarReporteRequest;
import com.clinica.expedientes.dto.response.ReportePendienteResponse;
import com.clinica.expedientes.dto.response.ReporteResponse;
import com.clinica.expedientes.security.UsuarioAutenticado;
import com.clinica.expedientes.service.SupervisorService;
import com.clinica.expedientes.service.TerapeutaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SupervisorController {

    private final SupervisorService supervisorService;
    private final TerapeutaService terapeutaService;

    @GetMapping("/supervisores/mis-reportes-pendientes")
    @PreAuthorize("hasRole('SUPERVISOR')")
    public ResponseEntity<List<ReportePendienteResponse>> misPendientes(
            @RequestParam(required = false) Long idTerapeuta,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta,
            @AuthenticationPrincipal UsuarioAutenticado supervisor) {
        return ResponseEntity.ok(supervisorService.listarPendientes(supervisor, idTerapeuta, fechaDesde, fechaHasta));
    }

    @GetMapping("/reportes/{idReporte}")
    @PreAuthorize("hasAnyRole('SUPERVISOR','TERAPEUTA')")
    public ResponseEntity<ReporteResponse> verReporte(
            @PathVariable Long idReporte,
            @AuthenticationPrincipal UsuarioAutenticado usuario) {
        if ("SUPERVISOR".equals(usuario.getRol()))
            return ResponseEntity.ok(supervisorService.verReporte(idReporte, usuario));
        return ResponseEntity.ok(terapeutaService.verReporte(idReporte, usuario));
    }

    @PatchMapping("/reportes/{idReporte}/aprobar")
    @PreAuthorize("hasRole('SUPERVISOR')")
    public ResponseEntity<ReporteResponse> aprobar(
            @PathVariable Long idReporte,
            @AuthenticationPrincipal UsuarioAutenticado supervisor) {
        return ResponseEntity.ok(supervisorService.aprobarReporte(idReporte, supervisor));
    }

    @PatchMapping("/reportes/{idReporte}/rechazar")
    @PreAuthorize("hasRole('SUPERVISOR')")
    public ResponseEntity<ReporteResponse> rechazar(
            @PathVariable Long idReporte,
            @Valid @RequestBody RechazarReporteRequest req,
            @AuthenticationPrincipal UsuarioAutenticado supervisor) {
        return ResponseEntity.ok(supervisorService.rechazarReporte(idReporte, req, supervisor));
    }
}
