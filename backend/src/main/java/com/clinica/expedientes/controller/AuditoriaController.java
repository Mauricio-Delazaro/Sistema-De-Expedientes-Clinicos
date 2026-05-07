package com.clinica.expedientes.controller;

import com.clinica.expedientes.model.RegistroAuditoria;
import com.clinica.expedientes.security.UsuarioAutenticado;
import com.clinica.expedientes.service.AuditoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auditoria")
@PreAuthorize("hasRole('ADMINISTRADOR')")
@RequiredArgsConstructor
public class AuditoriaController {

    private final AuditoriaService auditoriaService;

    @GetMapping
    public ResponseEntity<List<RegistroAuditoria>> consultar(
            @RequestParam(required = false) Long idUsuario,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaDesde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaHasta,
            @RequestParam(required = false) String accion,
            @RequestParam(required = false) String recurso,
            @RequestParam(required = false) String idRecurso,
            @RequestParam(required = false) String resultado,
            @AuthenticationPrincipal UsuarioAutenticado admin) {
        return ResponseEntity.ok(auditoriaService.consultar(
                idUsuario, fechaDesde, fechaHasta, accion, recurso, idRecurso, resultado));
    }
}
