package com.clinica.expedientes.controller;

import com.clinica.expedientes.dto.request.LoginRequest;
import com.clinica.expedientes.dto.response.LoginResponse;
import com.clinica.expedientes.model.UsuarioCache;
import com.clinica.expedientes.repository.UsuarioCacheRepository;
import com.clinica.expedientes.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioCacheRepository usuarioCacheRepository;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
        if (req.getIdUsuario() == null) {
            return ResponseEntity.badRequest().build();
        }

        UsuarioCache usuario = usuarioCacheRepository.findById(req.getIdUsuario()).orElse(null);

        if (usuario == null) {
            return ResponseEntity.status(401).build();
        }

        String rol = usuario.getTipo().name();
        String token = jwtService.generarToken(usuario.getIdUsuario(), usuario.getNombreCompleto(), rol);

        return ResponseEntity.ok(new LoginResponse(token, usuario.getIdUsuario(), usuario.getNombreCompleto(), rol));
    }
}
