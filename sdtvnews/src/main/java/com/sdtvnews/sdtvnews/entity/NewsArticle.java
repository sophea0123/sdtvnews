package com.sdtvnews.sdtvnews.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class NewsArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob  // Large text data type
    private String content;  // Store the article text

    @OneToMany(mappedBy = "newsArticle", cascade = CascadeType.ALL)
    private List<Image> images;  // List of associated images

}

