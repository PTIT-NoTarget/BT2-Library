package com.facenet.bt2.repos;

import com.facenet.bt2.entity.Author;
import com.facenet.bt2.entity.Book;
import com.facenet.bt2.entity.Category;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {
    public static Specification<Book> hasIsbn(String isbn) {
        return (book, cq, cb) -> cb.like(cb.lower(book.get("isbn")), "%" + isbn.toLowerCase() + "%");
    }

    public static Specification<Book> hasName(String name) {
        return (book, cq, cb) -> cb.like(cb.lower(book.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Book> hasNumOfPage(String numOfPage) {
        return (book, cq, cb) -> cb.like(cb.lower(book.get("numOfPage")), "%" + numOfPage.toLowerCase() + "%");
    }

    public static Specification<Book> hasAuthor(String author) {
        return (book, cq, cb) -> {
            Join<Book, Author> bookAuthorJoin = book.join("authors");
            Predicate authorNamePredicate = cb.like(cb.lower(bookAuthorJoin.get("name")), "%" + author.toLowerCase() + "%");
            return authorNamePredicate;
        };
    }

    public static Specification<Book> hasCategory(String category) {
        return (book, cq, cb) -> {
            Join<Book, Category> bookCategoryJoin = book.join("categories");
            Predicate categoryNamePredicate = cb.like(cb.lower(bookCategoryJoin.get("name")), "%" + category.toLowerCase() + "%");
            return categoryNamePredicate;
        };
    }
}
