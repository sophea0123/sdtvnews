package com.sdtvnews.sdtvnews.dto.request;

import com.sdtvnews.sdtvnews.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class RoleRequest {

    private Long id;

    private String name;  // Example: "ADMIN", "EMPLOYEE", "HR"

    private String description; // Optional description of the role

    private String status;

    private LocalDateTime createDate;

    private String createBy;

    private LocalDateTime deleteDate;

    private String deleteBy;

    private LocalDateTime updateDate;

    private String updateBy;

    private List<User> users;
}
