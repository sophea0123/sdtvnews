package com.sdtvnews.sdtvnews.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve images from D:/ads folder at /ads/**
        registry.addResourceHandler("/ads-image/**")
                .addResourceLocations("file:D:/ads/");

        // Serve images from D:/uploads folder at /uploads/**
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:D:/uploads/");
    }

}
