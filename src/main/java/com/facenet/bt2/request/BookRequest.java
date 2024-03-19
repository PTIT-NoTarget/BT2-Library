package com.facenet.bt2.request;

import com.facenet.bt2.dto.LibraryDto;
import com.facenet.bt2.entity.Author;
import com.facenet.bt2.entity.Category;
import com.facenet.bt2.entity.Library;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookRequest {
    private String isbn;
    private String name;
    private String dateOfPublic;
    private int numOfPage;
    private List<String> pictureUrls;
    private List<Integer>  authorIds;
    private List<Integer> categoryIds;
    private int libraryId;
}
