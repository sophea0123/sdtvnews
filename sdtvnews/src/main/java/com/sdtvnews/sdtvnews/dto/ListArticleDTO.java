package com.sdtvnews.sdtvnews.dto;

import java.time.LocalDateTime;

public interface ListArticleDTO {

    Long getId();
    String getTitle();
    LocalDateTime getCreateDate();
    String getName();
    String getFullName();

}
