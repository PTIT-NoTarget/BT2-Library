package com.facenet.bt2.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LibraryDto {
    private int id;
    private String name;
    private String address;
    private List<BookDto> books;
}
