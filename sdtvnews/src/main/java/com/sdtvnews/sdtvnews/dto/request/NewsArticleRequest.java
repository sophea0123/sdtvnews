package com.sdtvnews.sdtvnews.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
public class NewsArticleRequest {
    private String title;
    private String content;
    private Long cateId;
    private Long userId;
    private int scheduleStatus;
    private LocalDateTime scheduleDate;
    private MultipartFile[] images;
}
