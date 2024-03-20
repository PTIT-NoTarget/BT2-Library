package com.facenet.bt2.service.impl;

import com.facenet.bt2.dto.AuthorDto;
import com.facenet.bt2.entity.Author;
import com.facenet.bt2.repos.AuthorRepos;
import com.facenet.bt2.request.AuthorRequest;
import com.facenet.bt2.service.IAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthorService implements IAuthorService {
    @Autowired
    private AuthorRepos authorRepos;

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
    public void addAuthor(AuthorRequest authorRequest) {
        Author author = new Author();
        author.setName(authorRequest.getName());
        author.setDob(convertStringToTimestamp(authorRequest.getDob()));
        authorRepos.save(author);
    }

    @Override
    public void updateAuthor(int id, AuthorRequest authorRequest) {
        Author author = authorRepos.findById(id).get();
        author.setName(authorRequest.getName());
        author.setDob(convertStringToTimestamp(authorRequest.getDob()));
        authorRepos.save(author);
    }

    @Override
    public void deleteAuthor(int id) {
        authorRepos.deleteById(id);
    }

    @Override
    public Set<AuthorDto> getAllAuthor() {
        addAuthors();
        return authorRepos.findAll().stream().map(this::convertToDto).collect(Collectors.toSet());
    }

    @Override
    public AuthorDto getAuthorById(int id) {
        return convertToDto(authorRepos.findById(id).get());
    }

    @Override
    public AuthorDto convertToDto(Author author) {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(author.getId());
        authorDto.setName(author.getName());
        authorDto.setDob(author.getDob().toString());
        return authorDto;
    }

    @Override
    public void addAuthors() {
        Set<Author> authors = new HashSet<>(authorRepos.findAll());
        Author author1 = new Author();
        author1.setName("Nguyen Nhat Anh");
        author1.setDob(new Timestamp(0));
        Author author2 = new Author();
        author2.setName("Nguyen Nhat Anh");
        author2.setDob(new Timestamp(0));
        authors.add(author1);
        authors.add(author2);
        authorRepos.saveAll(authors);
    }
}
