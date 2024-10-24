package com.sdtvnews.sdtvnews.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CategoryRequest {

    private Long id;

    private String name;

    private String description;

    private String status;

    private String createBy;

    private String updateBy;

    private String deleteBy;

    private int indexShow;
}
