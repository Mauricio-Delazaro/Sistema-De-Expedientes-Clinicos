package com.clinica.expedientes.controller;

import com.clinica.expedientes.dto.request.RegistrarUsuarioRequest;
import com.clinica.expedientes.model.UsuarioCache;
import com.clinica.expedientes.repository.UsuarioCacheRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/internal")
@RequiredArgsConstructor
public class InternalController {

    private final UsuarioCacheRepository usuarioCacheRepository;

    @Value("${internal.secret}")
    private String internalSecret;

    @PostMapping("/usuarios")
    public ResponseEntity<Void> registrarUsuario(
            @RequestHeader("X-Internal-Secret") String secret,
            @Valid @RequestBody RegistrarUsuarioRequest req) {

        if (!internalSecret.equals(secret))
            return ResponseEntity.status(401).build();

        UsuarioCache cache = usuarioCacheRepository.findById(req.getIdUsuario())
                .orElse(new UsuarioCache());
        cache.setIdUsuario(req.getIdUsuario());
        cache.setNombreCompleto(req.getNombreCompleto());
        cache.setTipo(req.getTipo());
        usuarioCacheRepository.save(cache);

        return ResponseEntity.ok().build();
    }
}
