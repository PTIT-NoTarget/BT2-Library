package com.facenet.bt2.request;

import com.facenet.bt2.dto.LibraryDto;
import com.facenet.bt2.entity.Author;
import com.facenet.bt2.entity.Category;
import com.facenet.bt2.entity.Library;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookRequest {
    private int id;
    private String isbn;
    private String name;
    private Timestamp dateOfPublic;
    private int numPageOfBook;
    private List<String> pictureUrls;
    private List<String>  authorNames;
    private List<String> categoryNames;
    private int libraryId;
}
