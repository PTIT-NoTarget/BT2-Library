package com.facenet.bt2.repos;

import com.facenet.bt2.entity.Category;
import com.facenet.bt2.entity.Library;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepos extends JpaRepository<Category, Integer> {
    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "books",
                    "books.authors",
                    "books.libraries",
                    "books.pictures",
            }
    )
    Optional<Category> findById(int id);
}
