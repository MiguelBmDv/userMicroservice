package com.reto.usuario_microservice.application.handler;

import java.util.List;

import com.reto.usuario_microservice.application.dto.StaffRequest;
import com.reto.usuario_microservice.application.dto.StaffResponse;

public interface IStaffHandler {

    void saveUserInStaff(StaffRequest staffRequest);

    List<StaffResponse> getAllUserFromStaff();

    StaffResponse getUserFromStaff(Long userDocumentNumber);

    void updateUserInStaff (StaffRequest staffRequest);

    void deleteUserFromStaff (Long userDocumentNumber);

}
