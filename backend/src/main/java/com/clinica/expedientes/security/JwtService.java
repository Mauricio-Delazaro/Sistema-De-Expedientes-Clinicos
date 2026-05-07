package com.clinica.expedientes.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private static final long EXPIRACION_MS = 8 * 60 * 60 * 1000L; // 8 horas

    private final SecretKey key;

    public JwtService(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generarToken(Long idUsuario, String nombre, String rol) {
        Date ahora = new Date();
        return Jwts.builder()
                .subject(String.valueOf(idUsuario))
                .claim("nombre", nombre)
                .claim("rol", rol)
                .issuedAt(ahora)
                .expiration(new Date(ahora.getTime() + EXPIRACION_MS))
                .signWith(key)
                .compact();
    }

    public Claims parsear(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException e) {
            throw new JwtException("Token inválido o expirado", e);
        }
    }

    public Long extraerIdUsuario(Claims claims) {
        return Long.parseLong(claims.getSubject());
    }

    public String extraerRol(Claims claims) {
        return claims.get("rol", String.class);
    }

    public String extraerNombre(Claims claims) {
        return claims.get("nombre", String.class);
    }
}
