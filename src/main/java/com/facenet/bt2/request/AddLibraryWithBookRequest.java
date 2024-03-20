package com.facenet.bt2.request;

import com.facenet.bt2.dto.BookDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddLibraryWithBookRequest {
    String name;
    String address;
    List<BookRequest> books;
}
