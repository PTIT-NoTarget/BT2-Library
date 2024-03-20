package com.facenet.bt2.service;

import com.facenet.bt2.dto.AuthorDto;
import com.facenet.bt2.entity.Author;
import com.facenet.bt2.request.AuthorRequest;

import java.util.Set;

public interface IAuthorService {
    void addAuthor(AuthorRequest authorRequest);
    void updateAuthor(int id, AuthorRequest authorRequest);
    void deleteAuthor(int id);
    Set<AuthorDto> getAllAuthor();
    AuthorDto getAuthorById(int id);
    AuthorDto convertToDto(Author author);
    void addAuthors();
}
