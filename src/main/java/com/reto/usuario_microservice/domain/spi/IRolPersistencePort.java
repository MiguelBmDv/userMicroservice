package com.reto.usuario_microservice.domain.spi;

import java.util.List;

import com.reto.usuario_microservice.domain.model.Rol;

public interface IRolPersistencePort {

    Rol saveRol(Rol rol);

    List<Rol> getAllRols();

    Rol getRol (Long rolId);

    void updateRol (Rol rol);

    void deleteRol(Long rolId);
}
