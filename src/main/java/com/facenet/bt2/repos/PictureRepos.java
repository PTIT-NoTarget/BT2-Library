package com.facenet.bt2.repos;

import com.facenet.bt2.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepos extends JpaRepository<Picture, Integer> {
}
