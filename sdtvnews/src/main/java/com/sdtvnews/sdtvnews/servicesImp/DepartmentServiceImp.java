package com.sdtvnews.sdtvnews.servicesImp;

import com.sdtvnews.sdtvnews.config.CustomException;
import com.sdtvnews.sdtvnews.dto.request.DepartmentRequest;
import com.sdtvnews.sdtvnews.dto.response.DepartmentResponse;
import com.sdtvnews.sdtvnews.entity.Department;
import com.sdtvnews.sdtvnews.repository.DepartmentRepository;
import com.sdtvnews.sdtvnews.services.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImp implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public DepartmentRequest createDepartment(DepartmentRequest request) {
        // Check if the department already exists
        List<Department> existingCategories = departmentRepository.findAll();

        for (Department department : existingCategories) {
            if (department.getName().equalsIgnoreCase(request.getName())) {
                // Return a message if the department already exists
                throw new CustomException("Department with the name '" + request.getName() + "' already exists.");
            }
        }

        // Create a new Department if it doesn't exist
        Department newDepartment = new Department();
        newDepartment.setName(request.getName());
        newDepartment.setDescription(request.getDescription());
        newDepartment.setStatus("1");//1=active;0=dis-active
        newDepartment.setCreateBy(request.getCreateBy());
        newDepartment.setCreateDate(LocalDateTime.now());
        // Save the new department
        departmentRepository.save(newDepartment);

        return request; // or convert and return the saved entity as needed
    }

    @Override
    public List<DepartmentResponse> getAllDepartment() {
        List<Department> categories = departmentRepository.findAll(); // Retrieve all department from the repository
        List<DepartmentResponse> departmentResponses = new ArrayList<>();

        for (Department department : categories) {
            DepartmentResponse response = new DepartmentResponse();
            response.setId(department.getId());
            response.setName(department.getName());
            response.setDescription(department.getDescription());
            response.setStatus(department.getStatus());
            response.setCreateDate(department.getCreateDate());
            // Set other necessary fields

            departmentResponses.add(response);
        }

        return departmentResponses;
    }

    @Override
    public Optional<DepartmentResponse> getDepartmentById(Long id) {
        // Retrieve the department by its ID
        Optional<Department> departmentOptional = departmentRepository.findById(id);

        // Check if the department exists
        if (departmentOptional.isPresent()) {
            // Convert Department to DepartmentResponse if it exists
            Department department = departmentOptional.get();
            DepartmentResponse departmentResponse = new DepartmentResponse();
            departmentResponse.setId(department.getId());
            departmentResponse.setName(department.getName());
            departmentResponse.setDescription(department.getDescription());
            departmentResponse.setStatus(department.getStatus());
            departmentResponse.setCreateDate(department.getCreateDate());
            // Set other necessary fields

            return Optional.of(departmentResponse);
        } else {
            // Return an empty Optional if the department doesn't exist
            return Optional.empty();
        }
    }

    @Override
    public void updateDepartment(Long id, DepartmentRequest request) {
        // Find the department by its ID
        Optional<Department> departmentOptional = departmentRepository.findById(id);

        if (departmentOptional.isPresent()) {
            Department department = departmentOptional.get();
            // Update the department details
            department.setName(request.getName());
            department.setDescription(request.getDescription());
            department.setStatus(request.getStatus());
            department.setUpdateBy(request.getUpdateBy());
            department.setUpdateDate(LocalDateTime.now());

            // Save the updated department
            departmentRepository.save(department);
        } else {
            throw new CustomException("Department not found with ID: " + id);
        }
    }


    @Override
    public void updateDepartmentStatus(Long id, String status) {
        Optional<Department> departmentOptional = departmentRepository.findById(id);

        if (departmentOptional.isPresent()) {
            Department department = departmentOptional.get();
            department.setStatus(status); // Update the status (e.g., "0" for inactive)
            departmentRepository.save(department); // Save the updated department
        } else {
            throw new CustomException("Department not found with ID: " + id);
        }
    }

}
