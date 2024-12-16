package com.reto.usuario_microservice.infrastructure.output.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reto.usuario_microservice.infrastructure.output.jpa.entity.RolEntity;

public interface IRolRepository extends JpaRepository <RolEntity, Long>{

}
