package com.reto.usuario_microservice.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.reto.usuario_microservice.application.dto.StaffRequest;
import com.reto.usuario_microservice.domain.model.Rol;
import com.reto.usuario_microservice.domain.model.User;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface StaffRequestMapper {
    
    User toUser(StaffRequest staffRequest);
    
    @Mapping(target = "id", ignore = true) 
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "description", ignore = true) 

    Rol toRol(StaffRequest staffRequest);
}
