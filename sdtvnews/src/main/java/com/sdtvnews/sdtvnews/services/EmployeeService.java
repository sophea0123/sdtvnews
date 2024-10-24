package com.sdtvnews.sdtvnews.services;

import com.sdtvnews.sdtvnews.dto.request.EmployeeRequest;
import com.sdtvnews.sdtvnews.dto.response.EmployeeResponse;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    EmployeeRequest createEmployee(EmployeeRequest request);

    List<EmployeeResponse>getAllEmployee();

    Optional<EmployeeResponse>getEmployeeById(Long id);

    void updateEmployee(Long id, EmployeeRequest request);

    void updateEmployeeStatus(Long id, String status);

}
