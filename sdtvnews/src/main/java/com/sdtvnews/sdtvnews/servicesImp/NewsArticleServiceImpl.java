package com.sdtvnews.sdtvnews.servicesImp;


import com.sdtvnews.sdtvnews.config.CustomException;
import com.sdtvnews.sdtvnews.config.EncryptionImageUtil;
import com.sdtvnews.sdtvnews.config.EncryptionUtil;
import com.sdtvnews.sdtvnews.config.ImageUtil;
import com.sdtvnews.sdtvnews.dto.ListArticleDTO;
import com.sdtvnews.sdtvnews.dto.request.NewsArticleRequest;
import com.sdtvnews.sdtvnews.entity.NewsArticle;
import com.sdtvnews.sdtvnews.repository.NewsArticleRepository;
import com.sdtvnews.sdtvnews.services.NewsArticleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsArticleServiceImpl implements NewsArticleService {

    private final NewsArticleRepository newsArticleRepository;

    public NewsArticle createArticle(NewsArticleRequest request) throws IOException {
        String updatedContent = ImageUtil.processBase64Images(request.getContent(), request.getTitle());

        // Create a new NewsArticle object
        NewsArticle article = new NewsArticle();
        article.setTitle(request.getTitle());
        article.setContent(updatedContent);
        article.setCateId(request.getCateId());
        article.setCreateBy(request.getUserId());
        article.setCreateDate(LocalDateTime.now());
        article.setStatus("1");
        article.setStatusMarquee(request.getStatusMarquee());

        // Finally, save the article
        return newsArticleRepository.save(article);
    }

    public void updateSections(Long id, NewsArticleRequest request) throws IOException {
        String updateContent = ImageUtil.processBase64Images(request.getContent(), request.getTitle());

        Optional<NewsArticle> newsArticle = newsArticleRepository.findById(id);

        if (newsArticle.isPresent()) {
            NewsArticle article = newsArticle.get();
            // Update the article details
            article.setTitle(request.getTitle());
            article.setContent(updateContent);
            article.setCateId(request.getCateId());
            // Update the modification date if needed
            //article.setUpdateDate(LocalDateTime.now());

            // Save the updated article
            newsArticleRepository.save(article);
        } else {
            throw new CustomException("Sections not found with ID: " + id);
        }
    }


    @Override
    public List<ListArticleDTO> getAllArticles() {
        return newsArticleRepository.lstNewsArticle();
    }

    @Override
    public NewsArticle getArticleById(Long id) {
        return newsArticleRepository.findById(id).orElseThrow(() -> new RuntimeException("Article not found"));
    }

    public boolean isTitleDuplicate(String title) {
        // Check if the title exists in the database
        return newsArticleRepository.existsByTitle(title);
    }
    @Override
    public void deleteArticle(Long id) {
        newsArticleRepository.deleteById(id);
    }
}
