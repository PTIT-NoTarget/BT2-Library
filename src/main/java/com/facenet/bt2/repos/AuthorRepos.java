package com.facenet.bt2.repos;

import com.facenet.bt2.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepos extends JpaRepository<Author, Integer> {
}
