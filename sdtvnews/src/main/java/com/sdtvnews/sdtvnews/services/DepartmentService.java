package com.sdtvnews.sdtvnews.services;

import com.sdtvnews.sdtvnews.dto.request.DepartmentRequest;
import com.sdtvnews.sdtvnews.dto.response.DepartmentResponse;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    DepartmentRequest createDepartment(DepartmentRequest request);

    List<DepartmentResponse>getAllDepartment();

    Optional<DepartmentResponse>getDepartmentById(Long id);

    void updateDepartment(Long id, DepartmentRequest request);

    void updateDepartmentStatus(Long id, String status);

}
