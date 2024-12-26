package com.reto.usuario_microservice.infrastructure.input.rest;

import java.util.Base64;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reto.usuario_microservice.application.dto.StaffRequest;
import com.reto.usuario_microservice.application.handler.IStaffHandler;
import com.reto.usuario_microservice.infrastructure.output.jpa.adapter.jwt.JwtService;
import com.reto.usuario_microservice.infrastructure.output.jpa.adapter.security.CustomUserDetails;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final IStaffHandler staffHandler;

    @PostMapping("/login")
    public ResponseEntity<String> login(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Basic ")) {
            String base64Credentials = authorizationHeader.substring("Basic ".length());
            String credentials = new String(Base64.getDecoder().decode(base64Credentials));
            String[] values = credentials.split(":", 2);

            String email = values[0];
            String password = values[1];

            try {
                authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
                );
            } catch (BadCredentialsException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }

            final UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            if (userDetails instanceof CustomUserDetails) {
                CustomUserDetails customUser = (CustomUserDetails) userDetails;
                final String jwt = jwtService.generateTokenWithUserInfo(
                    userDetails,
                    customUser.getDocumentNumber(),
                    customUser.getName()
                );
                return ResponseEntity.ok(jwt);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("UserDetails instance is invalid");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Authorization header missing or incorrect");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Void> saveUserInStaff(@RequestBody StaffRequest staffRequest){
        staffHandler.saveUserInStaff(staffRequest, null, "/auth/register");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
