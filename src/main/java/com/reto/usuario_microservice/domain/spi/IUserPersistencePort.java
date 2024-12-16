package com.reto.usuario_microservice.domain.spi;

import java.util.List;

import com.reto.usuario_microservice.domain.model.User;

public interface IUserPersistencePort {

    void saveUser (User user);

    List<User> getAllUsers();

    User getUser(Long userDocumentNumber);

    void updateUser(User user);

    void deleteUser (Long userDocumentNumber);

}
