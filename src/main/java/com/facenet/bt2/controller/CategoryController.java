package com.facenet.bt2.controller;

import com.facenet.bt2.repos.CategoryRepos;
import com.facenet.bt2.request.CategoryRequest;
import com.facenet.bt2.request.LibraryRequest;
import com.facenet.bt2.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private CategoryRepos categoryRepos;
    @PostMapping("/add")
    public ResponseEntity<?> addLibrary(@RequestBody CategoryRequest categoryRequest) {
        if(categoryRequest.getName() == null || categoryRequest.getName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid add category request");
        }
        categoryService.addCategory(categoryRequest);
        return ResponseEntity.ok("Add category success");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateLibrary(@PathVariable int id, @RequestBody CategoryRequest categoryRequest) {
        if(categoryRequest.getName() == null || categoryRequest.getName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid update category request");
        }
        if(categoryRepos.findById(id).isPresent()) {
            categoryService.updateCategory(id, categoryRequest);
            return ResponseEntity.ok("Update category success");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteLibrary(@PathVariable int id) {
        if (categoryRepos.findById(id).isPresent()) {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok("Delete category success");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllLibrary() {
        return ResponseEntity.ok(categoryService.getAllCategory());
    }

}
