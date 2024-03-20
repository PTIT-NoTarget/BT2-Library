package com.facenet.bt2.service.impl;

import com.facenet.bt2.dto.BookDto;
import com.facenet.bt2.entity.*;
import com.facenet.bt2.repos.*;
import com.facenet.bt2.request.BookByLibraryRequest;
import com.facenet.bt2.request.BookRequest;
import com.facenet.bt2.request.SearchRequest;
import com.facenet.bt2.response.BookByLibraryResponse;
import com.facenet.bt2.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.File;
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
        Book book = new Book();
        book.setIsbn(bookRequest.getIsbn());
        book.setName(bookRequest.getName());
        book.setDateOfPublic(convertStringToTimestamp(bookRequest.getDateOfPublic()));
        book.setNumOfPage(bookRequest.getNumOfPage());
        Set<Library> libraries = new HashSet<>();
        libraries.add(libraryRepos.findById(bookRequest.getLibraryId()).get());
        book.setLibraries(libraries);
        List<Author> authorList = authorRepos.findAllById(bookRequest.getAuthorIds());
        Set<Author> authors = new HashSet<>(authorList);
        book.setAuthors(authors);
        List<Category> categoryList = categoryRepos.findAllById(bookRequest.getCategoryIds());
        Set<Category> categories = new HashSet<>(categoryList);
        book.setCategories(categories);
        Set<Picture> pictures = new HashSet<>(bookRequest.getPictureUrls().stream().map(url -> {
            Picture picture = new Picture();
            picture.setUrl(url);
            return picture;
        }).toList());
        book.setPictures(pictures);
        bookRepos.save(book);
    }

    @Override
    public void updateBook(int id, BookRequest bookRequest) {
        Book book = bookRepos.findById(id).get();
        book.setIsbn(bookRequest.getIsbn());
        book.setName(bookRequest.getName());
        book.setDateOfPublic(convertStringToTimestamp(bookRequest.getDateOfPublic()));
        book.setNumOfPage(bookRequest.getNumOfPage());
        List<Author> authorList = authorRepos.findAllById(bookRequest.getAuthorIds());
        Set<Author> authors = new HashSet<>(authorList);
        book.setAuthors(authors);
        Set<Category> categories = book.getCategories();
        if(categories == null) categories = new HashSet<>();
        List<Category> categoryList = categoryRepos.findAllById(bookRequest.getCategoryIds());
        categories.addAll(categoryList);
        book.setCategories(categories);
        Set<Picture> pictures = book.getPictures();
        if(pictures == null) pictures = new HashSet<>();
        pictures.addAll(bookRequest.getPictureUrls().stream().map(url -> {
            Picture picture = new Picture();
            picture.setUrl(url);
            return picture;
        }).toList());
        book.setPictures(pictures);
        bookRepos.save(book);
    }

    @Override
    public void deleteBook(int id) {
        bookRepos.deleteById(id);
    }

//    private String generateRandomString(int length) {
//        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
//        Random random = new Random();
//        StringBuilder stringBuilder = new StringBuilder(length);
//
//        for (int i = 0; i < length; i++) {
//            int randomIndex = random.nextInt(characters.length());
//            stringBuilder.append(characters.charAt(randomIndex));
//        }
//
//        return stringBuilder.toString();
//    }
//
//    private void fakeData(){
//        for(int i = 0; i < 10000; i++) {
//            Book book = new Book();
//            Random random = new Random();
//            book.setIsbn(random.nextInt(10000000) + "");
//            book.setName(generateRandomString(20));
//            book.setDateOfPublic(new Timestamp(System.currentTimeMillis()));
//            book.setNumOfPage(100 + random.nextInt(100));
//            Set<Library> libraries = new HashSet<>();
//            for(int j = 0; j < random.nextInt(5) + 1; j++){
//                libraries.add(libraryRepos.findById(random.nextInt(10) + 1));
//            }
//            book.setLibraries(libraries);
//            Set<Author> authors = new HashSet<>();
//            for(int j = 0; j < random.nextInt(3) + 1; j++){
//                authors.add(authorRepos.findById(random.nextInt(10) + 1).get());
//            }
//            book.setAuthors(authors);
//            Set<Category> categories = new HashSet<>();
//            for(int j = 0; j < random.nextInt(4) + 1; j++){
//                categories.add(categoryRepos.findById(random.nextInt(10) + 1).get());
//            }
//            book.setCategories(categories);
//            Picture picture = new Picture();
//            picture.setUrl("http://res.cloudinary.com/dwfqbhqcr/image/upload/v1710868751/library/cover%2C/e3zb4oc6exzx8xtsdb9b.jpg");
//            Set<Picture> pictures = new HashSet<>();
//            pictures.add(pictureRepos.save(picture));
//            book.setPictures(pictures);
//            bookRepos.save(book);
//        }
//    }

    @Override
    public Set<BookDto> getAllBooks() {

        return bookRepos.findAll().stream().map(book -> convertToDto(book, new AuthorService(), new CategoryService(), new PictureService())).collect(Collectors.toSet());
    }

    @Override
    public BookByLibraryResponse getAllBooksByLibraryId(BookByLibraryRequest bookByLibraryRequest) {
        BookByLibraryResponse bookByLibraryResponse = new BookByLibraryResponse();
        Pageable pageable = PageRequest.of(bookByLibraryRequest.getPageNumber(), bookByLibraryRequest.getPageSize());
        Page<Book> books = bookRepos.findAllByLibraries(libraryRepos.findById(bookByLibraryRequest.getLibraryId()).get(), pageable);
        bookByLibraryResponse.setBooks(books.stream().map(book -> convertToDto(book, new AuthorService(), new CategoryService(), new PictureService())).collect(Collectors.toSet()));
        bookByLibraryResponse.setTotalPage(books.getTotalPages());
        bookByLibraryResponse.setTotalElement(books.getTotalElements());
        return bookByLibraryResponse;
    }


    @Override
    public BookDto convertToDto(Book book, IAuthorService authorService, ICategoryService categoryService, IPictureService pictureService) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setName(book.getName());
        bookDto.setDateOfPublic(String.valueOf(book.getDateOfPublic()));
        bookDto.setNumOfPage(book.getNumOfPage());
        bookDto.setAuthors(book.getAuthors().stream().map(authorService::convertToDto).toList());
        bookDto.setCategories(book.getCategories().stream().map(categoryService::convertToDto).toList());
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
        return books.stream().map(book -> convertToDto(book, new AuthorService(), new CategoryService(), new PictureService())).collect(Collectors.toSet());
    }
}
