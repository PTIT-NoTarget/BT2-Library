package com.facenet.bt2.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookByLibraryRequest {
    private int pageNumber;
    private int pageSize;
    private int libraryId;
}
