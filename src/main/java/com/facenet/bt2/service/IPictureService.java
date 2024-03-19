package com.facenet.bt2.service;

import com.facenet.bt2.dto.PictureDto;
import com.facenet.bt2.entity.Picture;

public interface IPictureService {
    Picture addPicture(String url);

    PictureDto convertToDto(Picture picture);
}
