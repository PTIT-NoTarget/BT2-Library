package com.facenet.bt2.controller;

import com.facenet.bt2.repos.AuthorRepos;
import com.facenet.bt2.request.AuthorRequest;
import com.facenet.bt2.request.BookRequest;
import com.facenet.bt2.service.IAuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/author")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthorController {
    @Autowired
    private IAuthorService authorService;

    @Autowired
    private AuthorRepos authorRepos;

    @PostMapping("/add")
    public ResponseEntity<?> addBook(@RequestBody AuthorRequest authorRequest) {
        if(authorRequest.getName() == null || authorRequest.getName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid add author request");
        }
        authorService.addAuthor(authorRequest);
        return ResponseEntity.ok("Add author success");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBook(@PathVariable int id, @RequestBody AuthorRequest authorRequest) {
        if(authorRequest.getName() == null || authorRequest.getName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid update author request");
        }
        if(authorRepos.findById(id).isPresent()) {
            authorService.updateAuthor(id, authorRequest);
            return ResponseEntity.ok("Update author success");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author not found");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable int id) {
        if (authorRepos.findById(id).isPresent()) {
            authorService.deleteAuthor(id);
            return ResponseEntity.ok("Delete author success");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author not found");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllBook() {
        return ResponseEntity.ok(authorService.getAllAuthor());
    }

}
