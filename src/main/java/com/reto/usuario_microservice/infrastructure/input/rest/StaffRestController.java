package com.reto.usuario_microservice.infrastructure.input.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reto.usuario_microservice.application.dto.StaffRequest;
import com.reto.usuario_microservice.application.dto.StaffResponse;
import com.reto.usuario_microservice.application.handler.IStaffHandler;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/staff/")
@RequiredArgsConstructor
public class StaffRestController {

    private final IStaffHandler staffHandler;

    @PostMapping()
    public ResponseEntity<Void> saveUserInStaff(@RequestBody StaffRequest staffRequest){
        staffHandler.saveUserInStaff(staffRequest);
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
