package com.reto.usuario_microservice.domain.api;

import java.util.List;

import com.reto.usuario_microservice.domain.model.Rol;


public interface IRolServicePort {

    Rol saveRol(Rol rol);

    List<Rol> getAllRols();

    Rol getRol (Long rolId);

    void updateRol (Rol rol);

    void deleteRol(Long rolId);

}
