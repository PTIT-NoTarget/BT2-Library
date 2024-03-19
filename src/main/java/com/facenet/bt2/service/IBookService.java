package com.facenet.bt2.service;

import com.facenet.bt2.dto.BookDto;
import com.facenet.bt2.entity.Book;
import com.facenet.bt2.request.BookByLibraryRequest;
import com.facenet.bt2.request.BookRequest;
import com.facenet.bt2.request.SearchRequest;
import com.facenet.bt2.response.BookByLibraryResponse;

import java.util.List;
import java.util.Set;

public interface IBookService {
    void addBook(BookRequest bookRequest);
    void updateBook(int id, BookRequest bookRequest);
    void deleteBook(int id);
    Set<BookDto> getAllBooks();

    BookByLibraryResponse getAllBooksByLibraryId(BookByLibraryRequest bookByLibraryRequest);
    BookDto convertToDto(Book book, IAuthorService authorService, ICategoryService categoryService, IPictureService pictureService);
    Set<BookDto> searchBook(SearchRequest searchRequest);
}
