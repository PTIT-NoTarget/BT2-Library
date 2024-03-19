package com.facenet.bt2.request;

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
    List<String> books;
    //List<BookDto> books;
}
