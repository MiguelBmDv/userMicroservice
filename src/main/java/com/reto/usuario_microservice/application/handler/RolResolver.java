package com.reto.usuario_microservice.application.handler;

import org.springframework.stereotype.Component;

import com.reto.usuario_microservice.domain.constant.RolConstants;

@Component
public class RolResolver {
    public Long resolveRoleId(String roleName) {
        if (roleName == null) {
            return RolConstants.USER_ROL_ID; 
        }
        switch (roleName.toUpperCase()) {
            case "ADMIN":
                return RolConstants.ADMIN_ROL_ID;
            case "OWNER":
                return RolConstants.OWNER_ROL_ID;
            case "EMPLOYEE":
                return RolConstants.EMPLOYEE_ROL_ID;
            case "USER":
                return RolConstants.USER_ROL_ID;
            default:
                throw new IllegalArgumentException("Invalid role: " + roleName);
        }
    }
}
