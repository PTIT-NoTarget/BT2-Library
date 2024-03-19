package com.facenet.bt2.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UploadPictureRequest {
    private MultipartFile picture;
    private String folder;
}
