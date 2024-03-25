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

    private void addNewBooks(AddLibraryWithBookRequest addLibraryWithBookRequest, Library library){
        Map<String, Author> authorMap = authorRepos.findAll().stream().collect(Collectors.toMap(Author::getName, author -> author));
        Map<String, Category> categoryMap = categoryRepos.findAll().stream().collect(Collectors.toMap(Category::getName, category -> category));
        library.setName(addLibraryWithBookRequest.getName());
        library.setAddress(addLibraryWithBookRequest.getAddress());
        Set<Book> books = addLibraryWithBookRequest.getNewBooks().stream().map(bookRequest -> {
            Book book = new Book();
            book.setIsbn(bookRequest.getIsbn());
            book.setName(bookRequest.getName());
            book.setDateOfPublic(bookRequest.getDateOfPublic());
            book.setNumOfPage(bookRequest.getNumPageOfBook());
            book.setAuthors(bookRequest.getAuthorNames().stream().map(author -> {
                if(authorMap.containsKey(author)) {
                    return authorMap.get(author);
                }
                Author newAuthor = new Author();
                newAuthor.setName(author);
                return authorRepos.save(newAuthor);
            }).collect(Collectors.toSet()));

            book.setCategories(bookRequest.getCategoryNames().stream().map(category -> {
                if(categoryMap.containsKey(category)) {
                    return categoryMap.get(category);
                }
                Category newCategory = new Category();
                newCategory.setName(category);
                return categoryRepos.save(newCategory);
            }).collect(Collectors.toSet()));

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

    private void addExistedBooks(AddLibraryWithBookRequest addLibraryWithBookRequest, Library library){
        Map<Integer, Book> bookMap = bookRepos.findAll().stream().collect(Collectors.toMap(Book::getId, book -> book));
        library.setName(addLibraryWithBookRequest.getName());
        library.setAddress(addLibraryWithBookRequest.getAddress());
        Set<Book> books = addLibraryWithBookRequest.getExistedBookIds().stream().map(bookMap::get).collect(Collectors.toSet());
        library.setBooks(books);
    }

    @Override
    public void addLibraryWithBook(AddLibraryWithBookRequest addLibraryWithBookRequest) {
        Library library = new Library();
        addNewBooks(addLibraryWithBookRequest, library);
        addExistedBooks(addLibraryWithBookRequest, library);
        libraryRepos.save(library);
    }
}
