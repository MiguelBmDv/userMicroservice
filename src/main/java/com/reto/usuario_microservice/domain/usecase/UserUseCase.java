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
    public void saveUser(User user) {
        validateUser(user);
        validateRole(user.getRolId()); 
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
        validateAge(user.getDateOfBirth());
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
        // Verifica si el ID del rol es válido
        if (!isValidRolId(rolId)) {
            throw new IllegalArgumentException("El ID del rol proporcionado no es válido: " + rolId);
        }
    }
    
    private boolean isValidRolId(Long rolId) {
        // Compara con los IDs definidos en RolConstants
        return rolId.equals(RolConstants.ADMIN_ROL_ID) || 
               rolId.equals(RolConstants.EMPLOYEE_ROL_ID) || 
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
        // Aquí puedes agregar validaciones adicionales si necesitas, como un rango de valores específicos
        // Si, por ejemplo, el documento debe tener un número mínimo de dígitos, puedes hacer algo como:
        if (documentNumber.toString().length() < 7) {
            throw new IllegalArgumentException("El documento de identidad debe tener al menos 7 dígitos");
        }
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

}
