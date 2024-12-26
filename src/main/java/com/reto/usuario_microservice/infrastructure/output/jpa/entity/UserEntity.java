package com.reto.usuario_microservice.infrastructure.output.jpa.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "This field is mandatory")
    private String firstName;
    @NotNull(message = "This field is mandatory")
    private String lastName;
    @NotNull(message = "This field is mandatory")
    private Long documentNumber;
    @NotNull(message = "This field is mandatory")
    private String phone;
    @NotNull(message = "This field is mandatory")
    private LocalDate dateOfBirth;
    @NotNull(message = "This field is mandatory")
    private String email;
    @NotNull(message = "This field is mandatory")
    private String password;
    private Long rolId;
}
