package com.sdtvnews.sdtvnews.repository;

import com.sdtvnews.sdtvnews.entity.NewsArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsArticleRepository extends JpaRepository<NewsArticle,Long> {
}
