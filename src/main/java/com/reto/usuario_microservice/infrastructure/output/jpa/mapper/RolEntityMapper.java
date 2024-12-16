package com.reto.usuario_microservice.infrastructure.output.jpa.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.reto.usuario_microservice.domain.model.Rol;
import com.reto.usuario_microservice.infrastructure.output.jpa.entity.RolEntity;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RolEntityMapper {

    RolEntity toEntity(Rol rol);

    Rol toRol(RolEntity rolEntity);

    List<Rol> toRolList(List<RolEntity> rolEntityList);
}
