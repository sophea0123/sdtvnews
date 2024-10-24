package com.sdtvnews.sdtvnews.servicesImp;

import com.sdtvnews.sdtvnews.entity.Image;
import com.sdtvnews.sdtvnews.repository.ImageRepository;
import com.sdtvnews.sdtvnews.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    public void saveImages(List<Image> images) {
        imageRepository.saveAll(images);
    }
}
