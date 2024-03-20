package com.facenet.bt2.controller;

import com.facenet.bt2.request.AddLibraryWithBookRequest;
import com.facenet.bt2.request.LibraryRequest;
import com.facenet.bt2.service.ILibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/library")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LibraryController {
    @Autowired
    private ILibraryService libraryService;

    @PostMapping("/add")
    public ResponseEntity<?> addLibrary(@ModelAttribute LibraryRequest libraryRequest) {
        libraryService.addLibrary(libraryRequest);
        return ResponseEntity.ok("Add library success");
    }

    @PostMapping("/add-with-book")
    public ResponseEntity<?> addLibraryWithBook(@RequestBody AddLibraryWithBookRequest library) {
        libraryService.addLibraryWithBook(library);
        return ResponseEntity.ok("Add library with book success");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateLibrary(@PathVariable int id, @ModelAttribute LibraryRequest libraryRequest) {
        libraryService.updateLibrary(id, libraryRequest);
        return ResponseEntity.ok("Update library success");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteLibrary(@PathVariable int id) {
        libraryService.deleteLibrary(id);
        return ResponseEntity.ok("Delete library success");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllLibraryWithBook() {
        return new ResponseEntity<>(libraryService.getAllLibrary(), HttpStatus.OK);
    }
}
