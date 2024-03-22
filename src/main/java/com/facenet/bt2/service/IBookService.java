package com.facenet.bt2.service;

import com.facenet.bt2.dto.BookDto;
import com.facenet.bt2.entity.Book;
import com.facenet.bt2.request.*;
import com.facenet.bt2.response.BookResponse;

import java.util.Set;

public interface IBookService {
    void addBook(BookRequest bookRequest);
    void updateBook(int id, BookRequest bookRequest);
    void deleteBook(int id);
    Set<BookDto> getAllBooks();
    BookResponse getAllBooksByLibraryId(int libraryId, int pageNumber, int pageSize);
    BookResponse getAllBooksByCategoryId(int categoryId, int pageNumber, int pageSize);
    BookResponse getAllBooksByAuthorId(int authorId, int pageNumber, int pageSize);
    BookDto convertToDto(Book book, IAuthorService authorService, ICategoryService categoryService, IPictureService pictureService, ILibraryService libraryService);
    Set<BookDto> searchBook(SearchRequest searchRequest);
    Set<BookDto> searchBookByInput(String input);
}
