package com.facenet.bt2.response;

import com.facenet.bt2.dto.BookDto;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookResponse {
    int totalPage;
    long totalElement;
    Set<BookDto> books;
}
