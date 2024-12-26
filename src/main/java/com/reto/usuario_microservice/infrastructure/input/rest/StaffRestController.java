package com.reto.usuario_microservice.infrastructure.input.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reto.usuario_microservice.application.dto.StaffRequest;
import com.reto.usuario_microservice.application.dto.StaffResponse;
import com.reto.usuario_microservice.application.handler.IStaffHandler;
import com.reto.usuario_microservice.infrastructure.output.jpa.adapter.jwt.JwtService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/staff/")
@RequiredArgsConstructor
public class StaffRestController {

    private final IStaffHandler staffHandler;
    private final JwtService jwtService;
    
    @PostMapping()
    public ResponseEntity<Void> saveUserInStaff(@RequestBody StaffRequest staffRequest, @RequestHeader("Authorization") String authorizationHeader) {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("No se encontró un token JWT válido en el encabezado Authorization");
        }

        String token = authorizationHeader.substring(7);
        List<Map<String, String>> roles = jwtService.extractRoles(token);

        if (roles == null || roles.isEmpty()) {
            throw new IllegalArgumentException("El token JWT no contiene roles válidos");
        }

        String currentUserRoleAuthority = roles.get(0).get("authority");
        staffHandler.saveUserInStaff(staffRequest, currentUserRoleAuthority, "/staff");

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    

    @GetMapping()
    public ResponseEntity<List<StaffResponse>> getAllUserFromStaff(){
        return ResponseEntity.ok(staffHandler.getAllUserFromStaff());
    }
    
    @GetMapping("{document}")
    public ResponseEntity<StaffResponse> getUserFromStaff(@PathVariable(name = "document") Long userDocumentNumber){
        return ResponseEntity.ok(staffHandler.getUserFromStaff(userDocumentNumber));
    }

    @PutMapping()
    public ResponseEntity<Void> updateUserInStaff(@RequestBody StaffRequest staffRequest){
        staffHandler.updateUserInStaff(staffRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{documentNumber}")
    public ResponseEntity<Void> deleteUserFromStaff(@PathVariable(name = "documentNumber") Long userDocumentNumber){
        staffHandler.deleteUserFromStaff(userDocumentNumber);
        return ResponseEntity.noContent().build();
    }

}
