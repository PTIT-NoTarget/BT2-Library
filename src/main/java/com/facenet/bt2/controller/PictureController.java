package com.facenet.bt2.controller;

import com.facenet.bt2.request.LibraryRequest;
import com.facenet.bt2.request.UploadPictureRequest;
import com.facenet.bt2.service.ICloudinaryService;
import com.facenet.bt2.service.IPictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/picture")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PictureController {

    @Autowired
    private ICloudinaryService cloudinaryService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPicture(@ModelAttribute UploadPictureRequest picture) {
        return new ResponseEntity<>(cloudinaryService.uploadImage(picture.getPicture(), picture.getFolder()), HttpStatus.OK);
    }
}
