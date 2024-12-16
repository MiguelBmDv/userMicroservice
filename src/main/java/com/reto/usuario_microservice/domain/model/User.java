package com.reto.usuario_microservice.domain.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private Long documentNumber;
    private String phone;
    private LocalDate dateOfBirth;
    private String email;
    private String password;
    private Long rolId;

}
