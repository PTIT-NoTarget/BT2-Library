package com.facenet.bt2.service;

import com.facenet.bt2.dto.CategoryDto;
import com.facenet.bt2.entity.Category;
import com.facenet.bt2.request.CategoryRequest;

import java.util.Set;

public interface ICategoryService {
    void addCategory(CategoryRequest categoryRequest);
    void updateCategory(int id, CategoryRequest categoryRequest);
    void deleteCategory(int id);

    Set<CategoryDto> getAllCategory();
    CategoryDto getCategoryById(int id);
    CategoryDto convertToDto(Category category);
}
