package com.facenet.bt2.controller;

import com.facenet.bt2.repos.BookRepos;
import com.facenet.bt2.repos.LibraryRepos;
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
    @Autowired
    private BookRepos bookRepos;
    @Autowired
    private LibraryRepos libraryRepos;

    private boolean isValidateBookRequest(BookRequest bookRequest) {
        return bookRequest.getIsbn() == null || bookRequest.getIsbn().isEmpty()
            || bookRequest.getName() == null || bookRequest.getName().isEmpty()
            || bookRequest.getDateOfPublic() == null || bookRequest.getDateOfPublic().isEmpty()
            || bookRequest.getNumOfPage() <= 0
            || bookRequest.getAuthorIds() == null || bookRequest.getAuthorIds().isEmpty()
            || bookRequest.getCategoryIds() == null || bookRequest.getCategoryIds().isEmpty()
            || bookRequest.getPictureUrls() == null || bookRequest.getPictureUrls().isEmpty();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBook(@RequestBody BookRequest bookRequest) {
        if(isValidateBookRequest(bookRequest)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid add book request");
        }
        bookService.addBook(bookRequest);
        return ResponseEntity.ok("Add book success");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBook(@PathVariable int id, @RequestBody BookRequest bookRequest) {
        if(isValidateBookRequest(bookRequest)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid update book request");
        }
        if(bookRepos.findById(id).isPresent()) {
            bookService.updateBook(id, bookRequest);
            return ResponseEntity.ok("Update book success");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable int id) {
        if(bookRepos.findById(id).isPresent()) {
            bookService.deleteBook(id);
            return ResponseEntity.ok("Delete book success");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
    }

//    @PostMapping("/test")
//    public ResponseEntity<?> test(@RequestBody AddLibraryWithBookRequest addLibraryWithBookRequest) {
//        System.out.println(addLibraryWithBookRequest);
//        for(var i : addLibraryWithBookRequest.getBooks()) {
//            System.out.println(i);
//        }
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @GetMapping("/get-all-by-library")
    public ResponseEntity<?> getAllBooksByLibraryId(@RequestBody BookByLibraryRequest bookByLibraryRequest) {
        if(libraryRepos.findById(bookByLibraryRequest.getLibraryId()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Library not found");
        }
        return new ResponseEntity<>(bookService.getAllBooksByLibraryId(bookByLibraryRequest), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchBook(@RequestBody SearchRequest searchRequest) {
        return new ResponseEntity<>(bookService.searchBook(searchRequest), HttpStatus.OK);
    }
}
