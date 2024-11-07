package com.sdtvnews.sdtvnews.services;

import com.sdtvnews.sdtvnews.dto.ListArticleDTO;
import com.sdtvnews.sdtvnews.dto.request.NewsArticleRequest;
import com.sdtvnews.sdtvnews.dto.request.SectionsRequest;
import com.sdtvnews.sdtvnews.entity.NewsArticle;

import java.io.IOException;
import java.util.List;

public interface NewsArticleService {
    NewsArticle createArticle(NewsArticleRequest request)throws IOException;
    List<ListArticleDTO> getAllArticles();
    NewsArticle getArticleById(Long id);
    void updateSections(Long id, NewsArticleRequest request)throws IOException;
    void deleteArticle(Long id);
    boolean isTitleDuplicate(String title);
}

