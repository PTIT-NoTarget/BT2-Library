package com.facenet.bt2.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UploadPictureRequest {
    private List<MultipartFile> picture;
    private String folder;
}
