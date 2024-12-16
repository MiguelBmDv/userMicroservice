package com.reto.usuario_microservice.infrastructure.output.jpa.adapter;

import java.util.List;

import com.reto.usuario_microservice.domain.model.Rol;
import com.reto.usuario_microservice.domain.spi.IRolPersistencePort;
import com.reto.usuario_microservice.infrastructure.exception.NoDataFoundException;
import com.reto.usuario_microservice.infrastructure.exception.RolNotFoundException;
import com.reto.usuario_microservice.infrastructure.output.jpa.entity.RolEntity;
import com.reto.usuario_microservice.infrastructure.output.jpa.mapper.RolEntityMapper;
import com.reto.usuario_microservice.infrastructure.output.jpa.repository.IRolRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RolJpaAdapter implements IRolPersistencePort{

    private final IRolRepository rolRepository;
    private final RolEntityMapper rolEntityMapper;

    @Override
    public Rol saveRol(Rol rol) {
        return rolEntityMapper.toRol(rolRepository.save(rolEntityMapper.toEntity(rol)));
    }

    @Override
    public List<Rol> getAllRols() {
        List<RolEntity> rolEntityList = rolRepository.findAll();
        if(rolEntityList.isEmpty()){
            throw new NoDataFoundException();
        }

        return rolEntityMapper.toRolList(rolEntityList);
    }

    @Override
    public Rol getRol(Long rolId) {
        return rolEntityMapper.toRol(rolRepository.findById(rolId).orElseThrow(RolNotFoundException::new));
    }

    @Override
    public void updateRol(Rol rol) {
        rolRepository.save(rolEntityMapper.toEntity(rol));
    }

    @Override
    public void deleteRol(Long rolId) {
        rolRepository.deleteById(rolId);
        
    }
        
}
