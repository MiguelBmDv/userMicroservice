package com.reto.usuario_microservice.domain.usecase;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.reto.usuario_microservice.domain.api.IUserServicePort;
import com.reto.usuario_microservice.domain.constant.RolConstants;
import com.reto.usuario_microservice.domain.model.User;
import com.reto.usuario_microservice.domain.spi.IUserPersistencePort;

public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserUseCase(IUserPersistencePort userPersistencePort){
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public void saveUser(User user, String currentUserRoleAuthority, String endpoint) {
        validateUser(user);
        validateRole(user.getRolId()); 
        validateRoleForEndpoint(user, currentUserRoleAuthority, endpoint);
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        userPersistencePort.saveUser(user);
    }
    
    @Override
    public List<User> getAllUsers() {
        return userPersistencePort.getAllUsers();
    }

    @Override
    public User getUser(Long userDocumentNumber) {
        return userPersistencePort.getUser(userDocumentNumber);
    }

    @Override
    public void updateUser(User user) {
        validateUser(user);
        validateRole(user.getRolId());
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        userPersistencePort.updateUser(user);
    }

    @Override
    public void deleteUser(Long userDocumentNumber) {
       userPersistencePort.deleteUser(userDocumentNumber);
    }

    private void validateUser(User user) {
        validateEmail(user.getEmail());
        validatePhone(user.getPhone());
        validateDocumentNumber(user.getDocumentNumber());
        if (!isUserWithRole(user.getRolId())) {
            validateAge(user.getDateOfBirth());
        }
    }

    private void validateEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Email no válido");
        }
    }

    private void validateRole(Long rolId) {
        if (!isValidRolId(rolId)) {
            throw new IllegalArgumentException("El ID del rol proporcionado no es válido: " + rolId);
        }
    }
     

    private boolean isValidRolId(Long rolId) {
        return rolId.equals(RolConstants.ADMIN_ROL_ID) || 
               rolId.equals(RolConstants.EMPLOYEE_ROL_ID) || 
               rolId.equals(RolConstants.USER_ROL_ID) || 
               rolId.equals(RolConstants.OWNER_ROL_ID);
    }

    private void validatePhone(String phone) {
        if (phone.length() > 13 || !phone.matches("[+\\d]+")) {
            throw new IllegalArgumentException("Teléfono no válido");
        }
    }

    private void validateDocumentNumber(Long documentNumber) {
        if (documentNumber == null) {
            throw new IllegalArgumentException("El documento de identidad no puede ser nulo");
        }
        if (documentNumber.toString().length() < 7) {
            throw new IllegalArgumentException("El documento de identidad debe tener al menos 7 dígitos");
        }
    }

    private boolean isUserWithRole(Long rolId) {
        return rolId.equals(RolConstants.USER_ROL_ID);
    }

    private void validateAge(LocalDate birthDate) {
        LocalDate today = LocalDate.now();
        int age = today.getYear() - birthDate.getYear();
        if (today.getDayOfYear() < birthDate.getDayOfYear()) {
            age--;
        }
        if (age < 18) {
            throw new IllegalArgumentException("El usuario debe ser mayor de edad");
        }
    }


    private void validateRoleForEndpoint(User user, String currentUserRoleAuthority, String endpoint) {
        if ("ADMIN".equals(currentUserRoleAuthority)) {
            return; 
        }
        if (endpoint.equals("/auth/register")) {
            user.setRolId(RolConstants.USER_ROL_ID);
        } else if (endpoint.equals("/staff")) {
            if ("OWNER".equals(currentUserRoleAuthority)) {
                user.setRolId(RolConstants.EMPLOYEE_ROL_ID);
            } else {
                throw new IllegalArgumentException("No tiene permisos para crear usuarios en /staff");
            }
        } else {
            throw new IllegalArgumentException("Endpoint no reconocido para validación de roles");
        }
    }

}
