package com.clinica.expedientes.controller;

import com.clinica.expedientes.dto.request.*;
import com.clinica.expedientes.dto.response.ExpedienteResponse;
import com.clinica.expedientes.model.EntrevistaSocioeconomica;
import com.clinica.expedientes.model.Expediente;
import com.clinica.expedientes.model.InformeConsentimiento;
import com.clinica.expedientes.model.UsuarioCache;
import com.clinica.expedientes.security.UsuarioAutenticado;
import com.clinica.expedientes.service.AdministradorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@PreAuthorize("hasRole('ADMINISTRADOR')")
@RequiredArgsConstructor
public class AdministradorController {

    private final AdministradorService administradorService;

    @PostMapping("/expedientes")
    public ResponseEntity<?> crearExpediente(
            @Valid @RequestBody CrearExpedienteRequest req,
            @AuthenticationPrincipal UsuarioAutenticado admin) {
        Expediente exp = administradorService.crearExpediente(req, admin);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "idExpediente", exp.getIdExpediente(),
                "idPaciente", exp.getIdPaciente(),
                "idTerapeuta", exp.getIdTerapeuta(),
                "estado", exp.getEstado(),
                "fechaProxCita", exp.getFechaProxCita()
        ));
    }

    @PatchMapping("/expedientes/{idExpediente}/estado")
    public ResponseEntity<?> cambiarEstado(
            @PathVariable Long idExpediente,
            @Valid @RequestBody CambiarEstadoExpedienteRequest req,
            @AuthenticationPrincipal UsuarioAutenticado admin) {
        Expediente exp = administradorService.cambiarEstado(idExpediente, req, admin);
        return ResponseEntity.ok(Map.of(
                "idExpediente", exp.getIdExpediente(),
                "estado", exp.getEstado()
        ));
    }

    @PostMapping("/expedientes/{idExpediente}/entrevista-socioeconomica")
    public ResponseEntity<?> registrarEntrevista(
            @PathVariable Long idExpediente,
            @Valid @RequestBody EntrevistaSocioeconomicaRequest req,
            @AuthenticationPrincipal UsuarioAutenticado admin) {
        EntrevistaSocioeconomica e = administradorService.registrarEntrevista(idExpediente, req, admin);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "idDocumento", e.getIdDocumento(),
                "idExpediente", e.getExpediente().getIdExpediente(),
                "fecha", e.getFecha(),
                "ingresoFamiliar", e.getIngresoFamiliar(),
                "gastoAlimentacion", e.getGastoAlimentacion(),
                "lugarProcedencia", e.getLugarProcedencia(),
                "vivienda", e.getVivienda() == null ? "" : e.getVivienda(),
                "estadoSaludFamiliar", e.getEstadoSaludFamiliar()
        ));
    }

    @PostMapping("/expedientes/{idExpediente}/consentimiento")
    public ResponseEntity<?> registrarConsentimiento(
            @PathVariable Long idExpediente,
            @Valid @RequestBody ConsentimientoRequest req,
            @AuthenticationPrincipal UsuarioAutenticado admin) {
        InformeConsentimiento inf = administradorService.registrarConsentimiento(idExpediente, req, admin);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "idDocumento", inf.getIdDocumento(),
                "idExpediente", inf.getExpediente().getIdExpediente(),
                "fecha", inf.getFecha(),
                "cuerpoDelTexto", inf.getCuerpoDelTexto(),
                "acuerdoConfidencial", inf.getAcuerdoConfidencial()
        ));
    }

    @GetMapping("/administradores/expedientes/{id}")
    public ResponseEntity<ExpedienteResponse> verExpediente(
            @PathVariable Long id,
            @AuthenticationPrincipal UsuarioAutenticado admin) {
        return ResponseEntity.ok(administradorService.verExpediente(id, admin));
    }

    @GetMapping("/administradores/usuarios")
    public ResponseEntity<List<Map<String, Object>>> listarUsuarios(
            @RequestParam(required = false) UsuarioCache.TipoUsuario tipo) {
        return ResponseEntity.ok(administradorService.listarUsuarios(tipo));
    }
}
