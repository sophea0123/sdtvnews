package com.sdtvnews.sdtvnews.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@ToString
public class NewsArticleRequest {
    private Long id;
    private String title;
    private String content;
    private int cateId;
    private Long userId;
    private String statusMarquee;

}
