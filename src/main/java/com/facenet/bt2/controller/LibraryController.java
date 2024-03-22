package com.facenet.bt2.controller;

import com.facenet.bt2.dto.LibraryDto;
import com.facenet.bt2.repos.LibraryRepos;
import com.facenet.bt2.request.AddLibraryWithBookRequest;
import com.facenet.bt2.request.LibraryRequest;
import com.facenet.bt2.service.ILibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/library")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LibraryController {
    @Autowired
    private ILibraryService libraryService;

    @Autowired
    private LibraryRepos libraryRepos;

    @PostMapping("/add")
    public ResponseEntity<String> addLibrary(@RequestBody LibraryRequest libraryRequest) {
        if(libraryRequest.getName() == null || libraryRequest.getName().isEmpty()
        || libraryRequest.getAddress() == null || libraryRequest.getAddress().isEmpty()
        ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid add library request");
        }
        libraryService.addLibrary(libraryRequest);
        return ResponseEntity.ok("Add library success");
    }

    @PostMapping("/add-with-book")
    public ResponseEntity<String> addLibraryWithBook(@RequestBody AddLibraryWithBookRequest library) {
        if (library.getName() == null || library.getName().isEmpty()
        || library.getAddress() == null || library.getAddress().isEmpty()
        ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid add library with book request");
        }
        if((library.getNewBooks() == null || library.getNewBooks().isEmpty()) && (library.getExistedBookIds() == null || library.getExistedBookIds().isEmpty())){
            LibraryRequest libraryRequest = new LibraryRequest();
            libraryRequest.setName(library.getName());
            libraryRequest.setAddress(library.getAddress());
            libraryService.addLibrary(libraryRequest);
            return ResponseEntity.ok("Add library success");
        }
        libraryService.addLibraryWithBook(library);
        return ResponseEntity.ok("Add library with book success");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateLibrary(@PathVariable int id, @RequestBody LibraryRequest libraryRequest) {
        if(libraryRequest.getName() == null || libraryRequest.getName().isEmpty()
        || libraryRequest.getAddress() == null || libraryRequest.getAddress().isEmpty()
        ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid update library request");
        }
        if(libraryRepos.findById(id).isPresent()) {
            libraryService.updateLibrary(id, libraryRequest);
            return ResponseEntity.ok("Update library success");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Library not found");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLibrary(@PathVariable int id) {
        if (libraryRepos.findById(id).isPresent()) {
            libraryService.deleteLibrary(id);
            return ResponseEntity.ok("Delete library success");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Library not found");
    }

    @GetMapping("/all")
    public ResponseEntity<Set<LibraryDto>> getAllLibrary() {
        return new ResponseEntity<>(libraryService.getAllLibrary(), HttpStatus.OK);
    }
}
