package com.facenet.bt2.controller;

import com.facenet.bt2.request.AuthorRequest;
import com.facenet.bt2.request.BookRequest;
import com.facenet.bt2.service.IAuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/author")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthorController {
    @Autowired
    private IAuthorService authorService;

    @PostMapping("/add")
    public ResponseEntity<?> addBook(@ModelAttribute AuthorRequest authorRequest) {
        authorService.addAuthor(authorRequest);
        return ResponseEntity.ok("Add author success");
    }

}
