package com.facenet.bt2.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SearchRequest {
    private int pageNumber;
    private int pageSize;
    private String isbn;
    private String name;
    private String numOfPage;
    private String author;
    private String category;
    private int libraryId;
}
