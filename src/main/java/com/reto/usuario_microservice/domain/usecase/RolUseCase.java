package com.reto.usuario_microservice.domain.usecase;

import java.util.List;

import com.reto.usuario_microservice.domain.api.IRolServicePort;
import com.reto.usuario_microservice.domain.model.Rol;
import com.reto.usuario_microservice.domain.spi.IRolPersistencePort;


public class RolUseCase implements IRolServicePort{

    private final IRolPersistencePort rolPersistencePort;

    public RolUseCase(IRolPersistencePort rolPersistencePort){
        this.rolPersistencePort = rolPersistencePort;
    }

    @Override
    public Rol saveRol(Rol rol) {
        return rolPersistencePort.saveRol(rol);
    }

    @Override
    public List<Rol> getAllRols() {
        return rolPersistencePort.getAllRols();
    }

    @Override
    public Rol getRol(Long rolId) {
        return rolPersistencePort.getRol(rolId);
    }

    @Override
    public void updateRol(Rol rol) {
        rolPersistencePort.saveRol(rol);
    }

    @Override
    public void deleteRol(Long rolId) {
        rolPersistencePort.deleteRol(rolId);
    }

}
