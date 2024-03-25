package com.facenet.bt2.repos;

import com.facenet.bt2.entity.Author;
import com.facenet.bt2.entity.Library;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface AuthorRepos extends JpaRepository<Author, Integer> {
    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "books",
                    "books.libraries",
                    "books.categories",
                    "books.pictures",
            }
    )
    Optional<Author> findById(int id);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "books",
                    "books.libraries",
                    "books.categories",
                    "books.pictures",
            }
    )
    List<Author> findAll();
}
