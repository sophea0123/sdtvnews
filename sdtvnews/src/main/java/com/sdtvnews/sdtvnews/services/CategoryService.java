package com.sdtvnews.sdtvnews.services;

import com.sdtvnews.sdtvnews.dto.response.CategoryResponse;
import com.sdtvnews.sdtvnews.dto.request.CategoryRequest;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    CategoryRequest createCategory(CategoryRequest categoryRequest);

    List<CategoryResponse>getAllCategories();

    Optional<CategoryResponse>getCategoryById(Long id);

    void updateCategory(Long id, CategoryRequest categoryRequest);

    void updateCategoryStatus(Long id, String status);

}
