package com.sdtvnews.sdtvnews.servicesImp;

import com.sdtvnews.sdtvnews.config.CustomException;
import com.sdtvnews.sdtvnews.config.exception.ResourceNotFoundException;
import com.sdtvnews.sdtvnews.dto.request.EmployeeRequest;
import com.sdtvnews.sdtvnews.dto.response.EmployeeResponse;
import com.sdtvnews.sdtvnews.entity.Department;
import com.sdtvnews.sdtvnews.entity.Employee;
import com.sdtvnews.sdtvnews.repository.DepartmentRepository;
import com.sdtvnews.sdtvnews.repository.EmployeeRepository;
import com.sdtvnews.sdtvnews.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImp implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public EmployeeRequest createEmployee(EmployeeRequest request) {
        // Check if the employee already exists
        List<Employee> existingEmployees = employeeRepository.findAll();

        for (Employee employee : existingEmployees) {
            String fullName = employee.getFirstName() + " " + employee.getLastName();
            String requestFullName = request.getFirstName() + " " + request.getLastName();

            if (fullName.toLowerCase().contains(requestFullName.toLowerCase())) {
                // Return a message if a similar employee already exists
                throw new CustomException("An employee with a similar name '" + requestFullName + "' already exists.");
            }
        }

        Department department = departmentRepository.findById(
                request.getDepartmentId()).orElseThrow(()
                -> new ResourceNotFoundException("Bus not found"));

        // Create a new Employee if it doesn't exist
        Employee newEmployee = new Employee();
        newEmployee.setFirstName(request.getFirstName());
        newEmployee.setLastName(request.getLastName());
        newEmployee.setPosition(request.getPosition());
        newEmployee.setStatus("1");//1=active;0=dis-active
        newEmployee.setCreateBy(request.getCreateBy());
        newEmployee.setCreateDate(LocalDateTime.now());
        newEmployee.setDepartment(department);
        // Save the new employee
        employeeRepository.save(newEmployee);

        return request; // or convert and return the saved entity as needed
    }

    @Override
    public List<EmployeeResponse> getAllEmployee() {
        List<Employee> categories = employeeRepository.findAll(); // Retrieve all employee from the repository
        List<EmployeeResponse> employeeResponses = new ArrayList<>();

        for (Employee employee : categories) {
            EmployeeResponse response = new EmployeeResponse();
            response.setId(employee.getId());
            response.setFirstName(employee.getFirstName());
            response.setLastName(employee.getLastName());
            response.setStatus(employee.getStatus());
            response.setCreateDate(employee.getCreateDate());
            response.setPosition(employee.getPosition());
            // Set other necessary fields

            employeeResponses.add(response);
        }

        return employeeResponses;
    }

    @Override
    public Optional<EmployeeResponse> getEmployeeById(Long id) {
        // Retrieve the employee by its ID
        Optional<Employee> employeeOptional = employeeRepository.findById(id);

        // Check if the employee exists
        if (employeeOptional.isPresent()) {
            // Convert Employee to EmployeeResponse if it exists
            Employee employee = employeeOptional.get();
            EmployeeResponse employeeResponse = new EmployeeResponse();
            employeeResponse.setId(employee.getId());
            employeeResponse.setLastName(employee.getLastName());
            employeeResponse.setFirstName(employee.getLastName());
            employeeResponse.setPosition(employee.getPosition());
            employeeResponse.setStatus(employee.getStatus());
            employeeResponse.setCreateDate(employee.getCreateDate());
            // Set other necessary fields

            return Optional.of(employeeResponse);
        } else {
            // Return an empty Optional if the employee doesn't exist
            return Optional.empty();
        }
    }

    @Override
    public void updateEmployee(Long id, EmployeeRequest employeeRequest) {
        // Find the employee by its ID
        Optional<Employee> employeeOptional = employeeRepository.findById(id);

        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            // Update the employee details
            employee.setLastName(employeeRequest.getLastName());
            employee.setFirstName(employeeRequest.getFirstName());
            employee.setPosition(employeeRequest.getPosition());
            employee.setStatus(employeeRequest.getStatus());
            employee.setUpdateBy(employeeRequest.getUpdateBy());
            employee.setUpdateDate(LocalDateTime.now());

            // Save the updated employee
            employeeRepository.save(employee);
        } else {
            throw new CustomException("Employee not found with ID: " + id);
        }
    }


    @Override
    public void updateEmployeeStatus(Long id, String status) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);

        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            employee.setStatus(status); // Update the status (e.g., "0" for inactive)
            employeeRepository.save(employee); // Save the updated employee
        } else {
            throw new CustomException("Employee not found with ID: " + id);
        }
    }

}
