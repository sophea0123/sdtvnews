package com.sdtvnews.sdtvnews.dto.response;

import com.sdtvnews.sdtvnews.entity.Employee;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class DepartmentResponse {

    private Long id;

    private String name;  // Example: "HR", "IT", "Sales"

    private String description; // Optional description for the department

    private String status;

    private LocalDateTime createDate;

    private String createBy;

    private LocalDateTime deleteDate;

    private String deleteBy;

    private LocalDateTime updateDate;

    private String updateBy;

    private List<Employee> employees;
}
