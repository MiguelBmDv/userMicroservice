package com.reto.usuario_microservice.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.reto.usuario_microservice.domain.api.IRolServicePort;
import com.reto.usuario_microservice.domain.api.IUserServicePort;
import com.reto.usuario_microservice.domain.spi.IRolPersistencePort;
import com.reto.usuario_microservice.domain.spi.IUserPersistencePort;
import com.reto.usuario_microservice.domain.usecase.RolUseCase;
import com.reto.usuario_microservice.domain.usecase.UserUseCase;
import com.reto.usuario_microservice.infrastructure.output.jpa.adapter.RolJpaAdapter;
import com.reto.usuario_microservice.infrastructure.output.jpa.adapter.UserJpaAdapter;
import com.reto.usuario_microservice.infrastructure.output.jpa.mapper.RolEntityMapper;
import com.reto.usuario_microservice.infrastructure.output.jpa.mapper.UserEntityMapper;
import com.reto.usuario_microservice.infrastructure.output.jpa.repository.IRolRepository;
import com.reto.usuario_microservice.infrastructure.output.jpa.repository.IUserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IUserRepository userRepository;
    private final UserEntityMapper userEntityMapper;
    private final IRolRepository rolRepository;
    private final RolEntityMapper rolEntityMapper;

    @Bean
    public IUserPersistencePort userPersistencePort(){
        return new UserJpaAdapter(userRepository, userEntityMapper);
    }

    @Bean
    public IUserServicePort userServicePort(){
        return new UserUseCase(userPersistencePort());
    }

    @Bean
    public IRolPersistencePort rolPersistencePort(){
        return new RolJpaAdapter(rolRepository, rolEntityMapper);
    }

    @Bean
    public IRolServicePort rolServicePort(){
        return new RolUseCase(rolPersistencePort());
    }

}
