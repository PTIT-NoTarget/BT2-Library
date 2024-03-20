package com.facenet.bt2.service.impl;

import com.facenet.bt2.dto.BookDto;
import com.facenet.bt2.dto.LibraryDto;
import com.facenet.bt2.entity.*;
import com.facenet.bt2.repos.*;
import com.facenet.bt2.request.AddLibraryWithBookRequest;
import com.facenet.bt2.request.LibraryRequest;
import com.facenet.bt2.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LibraryService implements ILibraryService {
    @Autowired
    private LibraryRepos libraryRepos;

    @Autowired
    private AuthorRepos authorRepos;

    @Autowired
    private CategoryRepos categoryRepos;

    @Autowired
    private PictureRepos pictureRepos;

    @Autowired
    private BookRepos bookRepos;
    @Override
    public void addLibrary(LibraryRequest libraryRequest) {
        Library library = new Library();
        library.setName(libraryRequest.getName());
        library.setAddress(libraryRequest.getAddress());
        libraryRepos.save(library);
    }

    @Override
    public void updateLibrary(int id, LibraryRequest libraryRequest) {
        Library library = libraryRepos.findById(id).get();
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
        return libraryRepos.findAll().stream().map(this::convertToDto).collect(Collectors.toSet());
    }

    @Override
    public LibraryDto getLibraryById(int id) {
        return convertToDto(libraryRepos.findById(id).get());
    }

    public LibraryDto convertToDto(Library library) {
        LibraryDto libraryDto = new LibraryDto();
        libraryDto.setId(library.getId());
        libraryDto.setName(library.getName());
        libraryDto.setAddress(library.getAddress());
        return libraryDto;
    }

    @Override
    public void addLibraryWithBook(AddLibraryWithBookRequest addLibraryWithBookRequest) {
        Map<Integer, Author> authorMap = authorRepos.findAll().stream().collect(Collectors.toMap(Author::getId, author -> author));
        Map<Integer, Category> categoryMap = categoryRepos.findAll().stream().collect(Collectors.toMap(Category::getId, category -> category));
        Library library = new Library();
        library.setName(addLibraryWithBookRequest.getName());
        library.setAddress(addLibraryWithBookRequest.getAddress());
        Set<Book> books = addLibraryWithBookRequest.getBooks().stream().map(bookRequest -> {
            Book book = new Book();
            book.setIsbn(bookRequest.getIsbn());
            book.setName(bookRequest.getName());
            book.setDateOfPublic(convertStringToTimestamp(bookRequest.getDateOfPublic()));
            book.setNumOfPage(bookRequest.getNumOfPage());
            book.setAuthors(bookRequest.getAuthorIds().stream().map(authorMap::get).collect(Collectors.toSet()));
            book.setCategories(bookRequest.getCategoryIds().stream().map(categoryMap::get).collect(Collectors.toSet()));
            List<Picture> pictures = bookRequest.getPictureUrls().stream().map(url -> {
                Picture picture = new Picture();
                picture.setUrl(url);
                return picture;
            }).collect(Collectors.toList());
            book.setPictures(new HashSet<>(pictureRepos.saveAll(pictures)));
            return book;
        }).collect(Collectors.toSet());
        library.setBooks(new HashSet<>(bookRepos.saveAll(books)));
        libraryRepos.save(library);
    }
    private Timestamp convertStringToTimestamp(String strDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(strDate);
            return new Timestamp(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
