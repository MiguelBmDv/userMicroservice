package com.reto.usuario_microservice.infrastructure.output.jpa.adapter;

import java.util.List;

import com.reto.usuario_microservice.domain.model.User;
import com.reto.usuario_microservice.domain.spi.IUserPersistencePort;
import com.reto.usuario_microservice.infrastructure.exception.NoDataFoundException;
import com.reto.usuario_microservice.infrastructure.exception.UserAlreadyExistsException;
import com.reto.usuario_microservice.infrastructure.exception.UserNotFoundException;
import com.reto.usuario_microservice.infrastructure.output.jpa.entity.UserEntity;
import com.reto.usuario_microservice.infrastructure.output.jpa.mapper.UserEntityMapper;
import com.reto.usuario_microservice.infrastructure.output.jpa.repository.IUserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort{

    private final IUserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public void saveUser(User user) {
        if (userRepository.findByDocumentNumber(user.getDocumentNumber()).isPresent()){
            throw new UserAlreadyExistsException();
        }

        userRepository.save(userEntityMapper.toEntity(user));
    }

    @Override
    public List<User> getAllUsers() {
        List<UserEntity> userEntityList = userRepository.findAll();
        if (userEntityList.isEmpty()){
            throw new NoDataFoundException();
        }
        return userEntityMapper.toUserList(userEntityList);
    }

    @Override
    public User getUser(Long userDocumentNumber) {
        return userEntityMapper.toUser(userRepository.findByDocumentNumber(userDocumentNumber)
                .orElseThrow(UserNotFoundException::new));
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(userEntityMapper.toEntity(user));
    }

    @Override
    public void deleteUser(Long userDocumentNumber) {
        userRepository.deleteByDocumentNumber(userDocumentNumber);
    }

}
