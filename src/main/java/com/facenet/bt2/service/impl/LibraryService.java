package com.facenet.bt2.service.impl;

import com.facenet.bt2.dto.BookDto;
import com.facenet.bt2.dto.LibraryDto;
import com.facenet.bt2.entity.Book;
import com.facenet.bt2.entity.Library;
import com.facenet.bt2.repos.LibraryRepos;
import com.facenet.bt2.request.AddLibraryWithBookRequest;
import com.facenet.bt2.request.LibraryRequest;
import com.facenet.bt2.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LibraryService implements ILibraryService {
    @Autowired
    private LibraryRepos libraryRepos;
    @Override
    public void addLibrary(LibraryRequest libraryRequest) {
        Library library = new Library();
        library.setName(libraryRequest.getName());
        library.setAddress(libraryRequest.getAddress());
        libraryRepos.save(library);
    }

    @Override
    public void updateLibrary(int id, LibraryRequest libraryRequest) {
        Library library = libraryRepos.findById(id);
        library.setName(libraryRequest.getName());
        library.setAddress(libraryRequest.getAddress());
        libraryRepos.save(library);
    }

    @Override
    public void deleteLibrary(int id) {
        libraryRepos.deleteById(id);
    }
    @Override
    public Set<LibraryDto> getAllLibrary() {
        return libraryRepos.findAll().stream().map(library -> convertToDto(library, new BookService())).collect(Collectors.toSet());
    }

    @Override
    public LibraryDto getLibraryById(int id) {
        return convertToDto(libraryRepos.findById(id), new BookService());
    }

    public LibraryDto convertToDto(Library library, IBookService bookService) {
        LibraryDto libraryDto = new LibraryDto();
        libraryDto.setId(library.getId());
        libraryDto.setName(library.getName());
        libraryDto.setAddress(library.getAddress());
        //libraryDto.setBooks(library.getBooks().stream().map(book -> bookService.convertToDto(book, new AuthorService(), new CategoryService(), new PictureService())).toList());
        return libraryDto;
    }

    @Override
    public void addLibraryWithBook(AddLibraryWithBookRequest library) {

    }
}
