package com.sdtvnews.sdtvnews.services;

import com.sdtvnews.sdtvnews.dto.request.NewsArticleRequest;
import com.sdtvnews.sdtvnews.entity.NewsArticle;

import java.io.IOException;
import java.util.List;

public interface NewsArticleService {
    NewsArticle createArticle(NewsArticleRequest request)throws IOException;
    List<NewsArticle> getAllArticles();
    NewsArticle getArticleById(Long id);
    void deleteArticle(Long id);
}

