package com.clinica.expedientes.controller;

import com.clinica.expedientes.dto.response.LogAuditoriaResponse;
import com.clinica.expedientes.exception.AccesoDenegadoException;
import com.clinica.expedientes.exception.RecursoNoEncontradoException;
import com.clinica.expedientes.model.Administrador;
import com.clinica.expedientes.model.Usuario;
import com.clinica.expedientes.repository.UsuarioRepository;
import com.clinica.expedientes.service.AuditoriaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auditoria")
public class AuditoriaController {

    private final AuditoriaService auditoriaService;
    private final UsuarioRepository usuarioRepo;

    public AuditoriaController(AuditoriaService auditoriaService, UsuarioRepository usuarioRepo) {
        this.auditoriaService = auditoriaService;
        this.usuarioRepo = usuarioRepo;
    }

    // RF-12: Consultar registros de auditoría (ADMINISTRADOR)
    @GetMapping
    public ResponseEntity<List<LogAuditoriaResponse>> consultar(
            @RequestHeader("X-User-Id") Long userId,
            @RequestParam(required = false) Long idUsuario,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime fechaDesde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime fechaHasta,
            @RequestParam(required = false) String accion,
            @RequestParam(required = false) String recurso,
            @RequestParam(required = false) String idRecurso,
            @RequestParam(required = false) String resultado) {

        Usuario u = usuarioRepo.findById(userId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado: " + userId));
        if (!(u instanceof Administrador)) {
            throw new AccesoDenegadoException("Solo el ADMINISTRADOR puede consultar los registros de auditoría.");
        }

        return ResponseEntity.ok(auditoriaService.consultar(
                idUsuario, fechaDesde, fechaHasta, accion, recurso, idRecurso, resultado));
    }
}
