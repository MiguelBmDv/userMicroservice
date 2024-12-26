package com.reto.usuario_microservice.infrastructure.output.jpa.adapter.jwt;

import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;



    @Override
    @SuppressWarnings("null")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Evitar la validación en rutas públicas
        if (request.getRequestURI().startsWith("/auth/") || request.getRequestURI().startsWith("/login/")) {
            filterChain.doFilter(request, response);
            return; // No procesar el JWT en estas rutas
        }

        String jwt = extractJwtFromRequest(request);
        if (jwt != null) {
            String username = jwtService.extractUsername(jwt); // Extraemos el username del token

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username); // Cargar detalles del usuario

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    // Si el token es válido, establecer el contexto de seguridad
                    UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        // Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }

    // Extraer el token JWT del encabezado de la solicitud
    private String extractJwtFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); // Eliminar "Bearer " del inicio
        }
        return null;
    }

}
