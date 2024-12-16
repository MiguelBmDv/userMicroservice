package com.reto.usuario_microservice.application.handler;

import java.util.List;

import org.springframework.stereotype.Service;

import com.reto.usuario_microservice.application.dto.StaffRequest;
import com.reto.usuario_microservice.application.dto.StaffResponse;
import com.reto.usuario_microservice.application.mapper.StaffRequestMapper;
import com.reto.usuario_microservice.application.mapper.StaffResponseMapper;
import com.reto.usuario_microservice.domain.api.IRolServicePort;
import com.reto.usuario_microservice.domain.api.IUserServicePort;
import com.reto.usuario_microservice.domain.model.Rol;
import com.reto.usuario_microservice.domain.model.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class StaffHandler implements IStaffHandler  {

    private final IUserServicePort userServicePort;
    private final IRolServicePort rolServicePort;
    private final StaffRequestMapper staffRequestMapper;
    private final StaffResponseMapper staffResponseMapper;
    private final RolResolver rolResolver;

    @Override
    public void saveUserInStaff(StaffRequest staffRequest) {
        Long rolId = rolResolver.resolveRoleId(staffRequest.getRol());
        User user = staffRequestMapper.toUser(staffRequest);
        user.setRolId(rolId);
        userServicePort.saveUser(user);
    }

    @Override
    public List<StaffResponse> getAllUserFromStaff() {
        return staffResponseMapper.toResponseList(userServicePort.getAllUsers(),  rolServicePort.getAllRols());
    }

    
    @Override
    public StaffResponse getUserFromStaff(Long userDocumentNumber) {
        User user = userServicePort.getUser(userDocumentNumber);
        Rol rol = rolServicePort.getRol(user.getRolId());
        String rolName = (rol != null) ? rol.getName() : null;

        StaffResponse staffResponse = staffResponseMapper.toResponse(user);
        staffResponse.setRol(rolName);
        return staffResponse;
    }

    @Override
    public void updateUserInStaff(StaffRequest staffRequest) {
        User oldUser = userServicePort.getUser(staffRequest.getDocumentNumber());
        Long rolId = rolResolver.resolveRoleId(staffRequest.getRol());
        User newUser = staffRequestMapper.toUser(staffRequest);
        newUser.setId(oldUser.getId()); 
        newUser.setRolId(rolId); 
        userServicePort.updateUser(newUser);
    }


    @Override
    public void deleteUserFromStaff(Long userDocumentNumber) {
        userServicePort.deleteUser(userDocumentNumber);
    }


}
