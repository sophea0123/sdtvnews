package com.sdtvnews.sdtvnews.servicesImp;


import com.sdtvnews.sdtvnews.dto.request.NewsArticleRequest;
import com.sdtvnews.sdtvnews.entity.Image;
import com.sdtvnews.sdtvnews.entity.NewsArticle;
import com.sdtvnews.sdtvnews.repository.ImageRepository;
import com.sdtvnews.sdtvnews.repository.NewsArticleRepository;
import com.sdtvnews.sdtvnews.services.NewsArticleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsArticleServiceImpl implements NewsArticleService {

    private final NewsArticleRepository newsArticleRepository;
    private final ImageRepository imageRepository;

    private final String UPLOAD_DIR = "D:/uploads/";


    public NewsArticle createArticle(NewsArticleRequest request) throws IOException {
        // Create a new NewsArticle object
        NewsArticle article = new NewsArticle();
        article.setTitle(request.getTitle());
        article.setContent(request.getContent());

        // Handle file uploads and associate images
        List<Image> images = new ArrayList<>();
        for (MultipartFile file : request.getImages()) {
            if (!file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR + fileName);

                // Save the file to the specified directory
                Files.write(filePath, file.getBytes());

                // Create an Image object and set its properties
                Image image = new Image();
                image.setImagePath(filePath.toString()); // or use fileName based on your requirements
                image.setNewsArticle(article); // Associate the image with the article

                // Add the image to the list
                images.add(image);
            }
        }

        // Set images to the article
        article.setImages(images);

        // Save all images in the database
        imageRepository.saveAll(images); // Save images to the database

        // Finally, save the article
        return newsArticleRepository.save(article);
    }


    @Override
    public List<NewsArticle> getAllArticles() {
        return newsArticleRepository.findAll();
    }

    @Override
    public NewsArticle getArticleById(Long id) {
        return newsArticleRepository.findById(id).orElseThrow(() -> new RuntimeException("Article not found"));
    }

    @Override
    public void deleteArticle(Long id) {
        newsArticleRepository.deleteById(id);
    }
}
