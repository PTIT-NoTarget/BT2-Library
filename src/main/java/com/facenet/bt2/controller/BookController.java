package com.facenet.bt2.controller;

import com.facenet.bt2.dto.BookDto;
import com.facenet.bt2.repos.AuthorRepos;
import com.facenet.bt2.repos.BookRepos;
import com.facenet.bt2.repos.CategoryRepos;
import com.facenet.bt2.repos.LibraryRepos;
import com.facenet.bt2.request.*;
import com.facenet.bt2.response.BookResponse;
import com.facenet.bt2.service.IBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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
    @Autowired
    private AuthorRepos authorRepos;
    @Autowired
    private CategoryRepos categoryRepos;

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
    public ResponseEntity<String> addBook(@RequestBody BookRequest bookRequest) {
        if(isValidateBookRequest(bookRequest)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid add book request");
        }
        bookService.addBook(bookRequest);
        return ResponseEntity.ok("Add book success");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateBook(@PathVariable int id, @RequestBody BookRequest bookRequest) {
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
    public ResponseEntity<String> deleteBook(@PathVariable int id) {
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

    @GetMapping("/get-all-by-library/{libraryId}")
    public ResponseEntity<BookResponse> getAllBooksByLibraryId(@PathVariable int libraryId,
                                                                @RequestParam("page") int page,
                                                                @RequestParam("size") int size
                                                               ) {
        if(libraryRepos.findById(libraryId).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bookService.getAllBooksByLibraryId(libraryId,page,size), HttpStatus.OK);
    }

    @GetMapping("/get-all-by-category/{categoryId}")
    public ResponseEntity<BookResponse> getAllBooksByCategoryId(@PathVariable int categoryId,
                                                                @RequestParam("page") int page,
                                                                @RequestParam("size") int size
                                                                  ) {
          if(categoryRepos.findById(categoryId).isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
          }
          return new ResponseEntity<>(bookService.getAllBooksByCategoryId(categoryId,page,size), HttpStatus.OK);
    }

    @GetMapping("/get-all-by-author/{authorId}")
    public ResponseEntity<BookResponse> getAllBooksByAuthorId(@PathVariable int authorId,
                                                              @RequestParam("page") int page,
                                                              @RequestParam("size") int size) {
            if(authorRepos.findById(authorId).isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(bookService.getAllBooksByAuthorId(authorId,page,size), HttpStatus.OK);
        }

    @GetMapping("/search")
    public ResponseEntity<Set<BookDto>> searchBook(@RequestBody SearchRequest searchRequest) {
        return new ResponseEntity<>(bookService.searchBook(searchRequest), HttpStatus.OK);
    }
}
