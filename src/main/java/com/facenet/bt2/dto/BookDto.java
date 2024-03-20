package com.facenet.bt2.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookDto {
    private int id;
    private String isbn;
    private String name;
    private String dateOfPublic;
    private int numOfPage;
    private List<PictureDto> pictures;
    private List<CategoryDto> categories;
    private List<AuthorDto> authors;
    private List<LibraryDto> libraries;
}
