package com.facenet.bt2.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Table(name = "Author")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "dob")
    private Timestamp dob;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Book_Author",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Book> books;
}
