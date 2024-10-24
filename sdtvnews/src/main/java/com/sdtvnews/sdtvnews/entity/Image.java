package com.sdtvnews.sdtvnews.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imagePath;  // Path or URL of the image

    @ManyToOne
    @JoinColumn(name = "news_article_id")
    private NewsArticle newsArticle;  // Link to the article

}

