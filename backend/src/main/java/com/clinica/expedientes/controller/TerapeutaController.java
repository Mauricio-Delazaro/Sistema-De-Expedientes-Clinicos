package com.clinica.expedientes.controller;

import com.clinica.expedientes.dto.response.PacienteResumenResponse;
import com.clinica.expedientes.service.TerapeutaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/terapeutas")
public class TerapeutaController {

    private final TerapeutaService terapeutaService;

    public TerapeutaController(TerapeutaService terapeutaService) {
        this.terapeutaService = terapeutaService;
    }

    // CU-01: Visualizar pacientes asignados
    @GetMapping("/mis-pacientes")
    public ResponseEntity<List<PacienteResumenResponse>> getMisPacientes(
            @RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(terapeutaService.getMisPacientes(userId));
    }
}
