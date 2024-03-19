package com.facenet.bt2.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Table(name = "Picture")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "url")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "Book_Picture",
            joinColumns = @JoinColumn(name = "picture_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Book book;
}
