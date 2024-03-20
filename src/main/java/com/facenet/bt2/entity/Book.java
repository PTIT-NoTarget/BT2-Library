package com.facenet.bt2.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Table(name = "Book")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "name")
    private String name;

    @Column(name = "dateOfPublic")
    private Timestamp dateOfPublic;

    @Column(name = "numOfPage")
    private int numOfPage;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Book_Picture",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "picture_id"))
    private Set<Picture> pictures;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Library_Book",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "library_id"))
    private Set<Library> libraries;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Book_Author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Book_Category",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories;



}
