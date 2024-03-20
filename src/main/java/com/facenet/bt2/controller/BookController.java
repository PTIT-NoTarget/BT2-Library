package com.facenet.bt2.controller;

import com.facenet.bt2.request.AddLibraryWithBookRequest;
import com.facenet.bt2.request.BookByLibraryRequest;
import com.facenet.bt2.request.BookRequest;
import com.facenet.bt2.request.SearchRequest;
import com.facenet.bt2.service.IBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BookController {
    @Autowired
    private IBookService bookService;

    @PostMapping("/add")
    public ResponseEntity<?> addBook(@ModelAttribute BookRequest bookRequest) {
        bookService.addBook(bookRequest);
        return ResponseEntity.ok("Add book success");
    }

    @PostMapping("/test")
    public ResponseEntity<?> test(@RequestBody AddLibraryWithBookRequest addLibraryWithBookRequest) {
        System.out.println(addLibraryWithBookRequest);
        for(var i : addLibraryWithBookRequest.getBooks()) {
            System.out.println(i);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get-all-by-library")
    public ResponseEntity<?> getAllBooksByLibraryId(@ModelAttribute BookByLibraryRequest bookByLibraryRequest) {
        return new ResponseEntity<>(bookService.getAllBooksByLibraryId(bookByLibraryRequest), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchBook(@ModelAttribute SearchRequest searchRequest) {
        return new ResponseEntity<>(bookService.searchBook(searchRequest), HttpStatus.OK);
    }
}
