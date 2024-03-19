package com.facenet.bt2.repos;

import com.facenet.bt2.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepos extends JpaRepository<Category, Integer> {
}
