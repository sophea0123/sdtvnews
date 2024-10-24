package com.sdtvnews.sdtvnews.dto.response;

import com.sdtvnews.sdtvnews.entity.Department;
import com.sdtvnews.sdtvnews.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class EmployeeResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String position;
    private LocalDate hireDate;

    private String status;
    private LocalDateTime createDate;
    private String createBy;
    private LocalDateTime deleteDate;
    private String deleteBy;
    private LocalDateTime updateDate;
    private String updateBy;

    private Long departmentId;

    private User user;
}
