package com.facenet.bt2.controller;

import com.facenet.bt2.request.CategoryRequest;
import com.facenet.bt2.request.LibraryRequest;
import com.facenet.bt2.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;
    @PostMapping("/add")
    public ResponseEntity<?> addLibrary(@ModelAttribute CategoryRequest categoryRequest) {
        categoryService.addCategory(categoryRequest);
        return ResponseEntity.ok("Add category success");
    }
}
