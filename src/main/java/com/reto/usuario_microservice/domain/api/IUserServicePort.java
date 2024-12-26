package com.reto.usuario_microservice.domain.api;

import java.util.List;

import com.reto.usuario_microservice.domain.model.User;

public interface IUserServicePort {

    void saveUser(User user, String currentUserRoleAuthority, String endpoint);

    List<User> getAllUsers();

    User getUser(Long userDocumentNumber);

    void updateUser(User user);

    void deleteUser (Long userDocumentNumber);

}   
