package com.reto.usuario_microservice.application.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.reto.usuario_microservice.application.dto.StaffResponse;
import com.reto.usuario_microservice.domain.model.Rol;
import com.reto.usuario_microservice.domain.model.User;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface  StaffResponseMapper {

    StaffResponse toResponse(User user);

    default List<StaffResponse> toResponseList(List<User> userList, List<Rol> rolList) {
        return userList.stream()
            .map(user -> {
                StaffResponse staffResponse = new StaffResponse();
                staffResponse.setId(user.getId());
                staffResponse.setFirstName(user.getFirstName());
                staffResponse.setLastName(user.getLastName());
                staffResponse.setEmail(user.getEmail());
                staffResponse.setDocumentNumber(user.getDocumentNumber());
                staffResponse.setPhone(user.getPhone());
                staffResponse.setDateOfBirth(user.getDateOfBirth());
                staffResponse.setPassword(user.getPassword());
                staffResponse.setRol(rolList.stream()
                    .filter(role -> role.getId().equals(user.getRolId()))
                    .findFirst()
                    .map(Rol::getName) 
                    .orElse(null)); 
                return staffResponse;
            }).toList();
    }
}
