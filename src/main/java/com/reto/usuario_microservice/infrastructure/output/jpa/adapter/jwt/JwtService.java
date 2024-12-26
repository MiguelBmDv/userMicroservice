package com.reto.usuario_microservice.infrastructure.output.jpa.adapter.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.*;

import java.security.Key;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKeyJwt;

    public String generateToken(UserDetails userDetails, Map<String, Object> additionalClaims) {
        Instant now = Instant.now(); // Fecha actual como Instant
        Instant expiresAt = now.plusSeconds(36000); // Expiración del token en 10 horas

        Map<String, Object> claims = new HashMap<>(additionalClaims);
        claims.put("roles", userDetails.getAuthorities());

        Key secretKey = new javax.crypto.spec.SecretKeySpec(secretKeyJwt.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiresAt))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateTokenWithUserInfo(UserDetails userDetails, Long documentNumber, String name) {
        Map<String, Object> additionalClaims = new HashMap<>();
        additionalClaims.put("documentNumber", documentNumber);
        additionalClaims.put("name", name);
        return generateToken(userDetails, additionalClaims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKeyJwt.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    public String extractDocumentNumber(String token) {
        return extractClaim(token, claims -> claims.get("documentNumber", String.class));
    }

    public String extractName(String token) {
        return extractClaim(token, claims -> claims.get("name", String.class));
    }
    
    @SuppressWarnings("unchecked")
    public List<Map<String, String>> extractRoles(String token) {
        return extractClaim(token, claims -> claims.get("roles", List.class));
    }
    
}
