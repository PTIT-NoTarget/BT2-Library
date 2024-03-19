package com.facenet.bt2.service.impl;

import com.facenet.bt2.dto.PictureDto;
import com.facenet.bt2.entity.Picture;
import com.facenet.bt2.repos.PictureRepos;
import com.facenet.bt2.service.IPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PictureService implements IPictureService {
    @Autowired
    private PictureRepos pictureRepos;
    @Override
    public Picture addPicture(String url) {
        Picture picture = new Picture();
        picture.setUrl(url);
        return pictureRepos.save(picture);
    }
    @Override
    public PictureDto convertToDto(Picture picture) {
        PictureDto pictureDto = new PictureDto();
        pictureDto.setId(picture.getId());
        pictureDto.setUrl(picture.getUrl());
        return pictureDto;
    }
}
