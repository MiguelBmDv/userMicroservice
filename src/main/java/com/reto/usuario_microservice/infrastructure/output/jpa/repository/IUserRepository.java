package com.reto.usuario_microservice.infrastructure.output.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reto.usuario_microservice.infrastructure.output.jpa.entity.UserEntity;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByDocumentNumber(Long userDocumentNumber);

    void deleteByDocumentNumber(Long userDocumentNumber);

}
