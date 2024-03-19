package com.facenet.bt2.service.impl;

import com.facenet.bt2.dto.CategoryDto;
import com.facenet.bt2.entity.Category;
import com.facenet.bt2.repos.CategoryRepos;
import com.facenet.bt2.request.CategoryRequest;
import com.facenet.bt2.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryRepos categoryRepos;

    @Override
    public void addCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());
        categoryRepos.save(category);
    }

    @Override
    public void updateCategory(int id, CategoryRequest categoryRequest) {
        Category category = categoryRepos.findById(id).get();
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());
        categoryRepos.save(category);
    }

    @Override
    public void deleteCategory(int id) {
        categoryRepos.deleteById(id);
    }

    @Override
    public Set<CategoryDto> getAllCategory() {
        return categoryRepos.findAll().stream().map(this::convertToDto).collect(Collectors.toSet());
    }

    @Override
    public CategoryDto getCategoryById(int id) {
        return convertToDto(categoryRepos.findById(id).get());
    }

    @Override
    public CategoryDto convertToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setDescription(category.getDescription());
        return categoryDto;
    }
}
