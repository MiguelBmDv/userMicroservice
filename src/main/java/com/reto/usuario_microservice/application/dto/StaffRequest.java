package com.reto.usuario_microservice.application.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StaffRequest {

    private Long id;
    private String firstName;
    private String lastName;
    private Long documentNumber;
    private String phone;
    private LocalDate dateOfBirth;
    private String email;
    private String password;
    private String rol;

}


