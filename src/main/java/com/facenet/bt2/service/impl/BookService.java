package com.facenet.bt2.service.impl;

import com.facenet.bt2.dto.BookDto;
import com.facenet.bt2.entity.*;
import com.facenet.bt2.repos.*;
import com.facenet.bt2.request.*;
import com.facenet.bt2.response.BookResponse;
import com.facenet.bt2.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService implements IBookService{

    @Autowired
    private BookRepos bookRepos;

    @Autowired
    private LibraryRepos libraryRepos;

    @Autowired
    private AuthorRepos authorRepos;

    @Autowired
    private CategoryRepos categoryRepos;

    @Autowired
    private PictureRepos pictureRepos;

    private Timestamp convertStringToTimestamp(String strDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = sdf.parse(strDate);
            return new Timestamp(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addBook(BookRequest bookRequest) {
//        Book book = new Book();
//        book.setIsbn(bookRequest.getIsbn());
//        book.setName(bookRequest.getName());
//        book.setDateOfPublic(convertStringToTimestamp(bookRequest.getDateOfPublic()));
//        book.setNumOfPage(bookRequest.getNumPageOfBook());
//        Set<Library> libraries = new HashSet<>();
//        libraries.add(libraryRepos.findById(bookRequest.getLibraryId()).get());
//        book.setLibraries(libraries);
//        List<Author> authorList = authorRepos.findAllById(bookRequest.getAuthorIds());
//        Set<Author> authors = new HashSet<>(authorList);
//        book.setAuthors(authors);
//        List<Category> categoryList = categoryRepos.findAllById(bookRequest.getCategoryIds());
//        Set<Category> categories = new HashSet<>(categoryList);
//        book.setCategories(categories);
//        Set<Picture> pictures = new HashSet<>(bookRequest.getPictureUrls().stream().map(url -> {
//            Picture picture = new Picture();
//            picture.setUrl(url);
//            return picture;
//        }).toList());
//        book.setPictures(pictures);
//        bookRepos.save(book);
    }

    @Override
    public void updateBook(int id, BookRequest bookRequest) {
//        Book book = bookRepos.findById(id).get();
//        book.setIsbn(bookRequest.getIsbn());
//        book.setName(bookRequest.getName());
//        book.setDateOfPublic(convertStringToTimestamp(bookRequest.getDateOfPublic()));
//        book.setNumOfPage(bookRequest.getNumPageOfBook());
//        List<Author> authorList = authorRepos.findAllById(bookRequest.getAuthorIds());
//        Set<Author> authors = new HashSet<>(authorList);
//        book.setAuthors(authors);
//        Set<Category> categories = book.getCategories();
//        if(categories == null) categories = new HashSet<>();
//        List<Category> categoryList = categoryRepos.findAllById(bookRequest.getCategoryIds());
//        categories.addAll(categoryList);
//        book.setCategories(categories);
//        Set<Picture> pictures = book.getPictures();
//        if(pictures == null) pictures = new HashSet<>();
//        pictures.addAll(bookRequest.getPictureUrls().stream().map(url -> {
//            Picture picture = new Picture();
//            picture.setUrl(url);
//            return picture;
//        }).toList());
//        book.setPictures(pictures);
//        bookRepos.save(book);
    }

    @Override
    public void deleteBook(int id) {
        bookRepos.deleteById(id);
    }

    @Override
    public Set<BookDto> getAllBooks() {
        return bookRepos.findAll().stream().map(book -> convertToDto(book, new AuthorService(), new CategoryService(), new PictureService(),null)).collect(Collectors.toSet());
    }

    @Override
    public BookResponse getAllBooksByLibraryId(int libraryId, int pageNumber, int pageSize) {
        BookResponse bookByLibraryResponse = new BookResponse();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Book> books = bookRepos.findAllByLibraries(libraryRepos.findById(libraryId).get(), pageable);
        bookByLibraryResponse.setBooks(books.stream().map(book -> convertToDto(book, new AuthorService(), new CategoryService(), new PictureService(),null)).collect(Collectors.toSet()));
        bookByLibraryResponse.setTotalPage(books.getTotalPages());
        bookByLibraryResponse.setTotalElement(books.getTotalElements());
        return bookByLibraryResponse;
    }

    @Override
    public BookResponse getAllBooksByCategoryId(int categoryId, int pageNumber, int pageSize) {
        BookResponse bookByCategoryResponse = new BookResponse();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Book> books = bookRepos.findAllByCategories(categoryRepos.findById(categoryId).get(), pageable);
        bookByCategoryResponse.setBooks(books.stream().map(book -> convertToDto(book, new AuthorService(), null, new PictureService(),new LibraryService())).collect(Collectors.toSet()));
        bookByCategoryResponse.setTotalPage(books.getTotalPages());
        bookByCategoryResponse.setTotalElement(books.getTotalElements());
        return bookByCategoryResponse;
    }

    @Override
    public BookResponse getAllBooksByAuthorId(int authorId, int pageNumber, int pageSize) {
        BookResponse bookByAuthorResponse = new BookResponse();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Book> books = bookRepos.findAllByAuthors(authorRepos.findById(authorId).get(), pageable);
        bookByAuthorResponse.setBooks(books.stream().map(book -> convertToDto(book, new AuthorService(), new CategoryService(), new PictureService(),new LibraryService())).collect(Collectors.toSet()));
        bookByAuthorResponse.setTotalPage(books.getTotalPages());
        bookByAuthorResponse.setTotalElement(books.getTotalElements());
        return bookByAuthorResponse;
    }


    @Override
    public BookDto convertToDto(Book book, IAuthorService authorService, ICategoryService categoryService, IPictureService pictureService, ILibraryService libraryService) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setName(book.getName());
        bookDto.setDateOfPublic(String.valueOf(book.getDateOfPublic()));
        bookDto.setNumOfPage(book.getNumOfPage());
        if(authorService != null) {
            bookDto.setAuthors(book.getAuthors().stream().map(authorService::convertToDto).toList());
        }
        if(categoryService != null) {
            bookDto.setCategories(book.getCategories().stream().map(categoryService::convertToDto).toList());
        }
        if(libraryService != null) {
            bookDto.setLibraries(book.getLibraries().stream().map(libraryService::convertToDto).toList());
        }
        if(book.getPictures() != null){
            bookDto.setPictures(book.getPictures().stream().map(pictureService::convertToDto).toList());
        }
        return bookDto;
    }

    @Override
    public Set<BookDto> searchBook(SearchRequest searchRequest) {
        Specification<Book> specBook = Specification.where(null);
        if(searchRequest.getIsbn() != null && !searchRequest.getIsbn().isEmpty()) {
            specBook = specBook.and(BookSpecification.hasIsbn(searchRequest.getIsbn()));
        }
        if(searchRequest.getName() != null && !searchRequest.getName().isEmpty()) {
            specBook = specBook.and(BookSpecification.hasName(searchRequest.getName()));
        }
        if(searchRequest.getNumOfPage() != null && !searchRequest.getNumOfPage().isEmpty()) {
            specBook = specBook.and(BookSpecification.hasNumOfPage(searchRequest.getNumOfPage()));
        }
        if(searchRequest.getAuthor() != null && !searchRequest.getAuthor().isEmpty()) {
            specBook = specBook.and(BookSpecification.hasAuthor(searchRequest.getAuthor()));
        }
        if(searchRequest.getCategory() != null && !searchRequest.getCategory().isEmpty()) {
            specBook = specBook.and(BookSpecification.hasCategory(searchRequest.getCategory()));
        }
        Pageable pageable = PageRequest.of(searchRequest.getPageNumber(), searchRequest.getPageSize());
        Page<Book> books = bookRepos.findAll(specBook, pageable);
        return books.stream().map(book -> convertToDto(book, new AuthorService(), new CategoryService(), new PictureService(),null)).collect(Collectors.toSet());
    }

    @Override
    public Set<BookDto> searchBookByInput(String input) {
        Specification<Book> specBook = Specification.where(null);
        specBook = specBook.and(BookSpecification.hasInput(input));
        List<Book> books = bookRepos.findAll(specBook, Sort.by("name"));
        return books.stream().map(book -> convertToDto(book, null, null, new PictureService(),null)).collect(Collectors.toSet());
    }
}
