package com.facenet.bt2.repos;

import com.facenet.bt2.entity.Library;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibraryRepos extends JpaRepository<Library, Integer> {
    List<Library> findAll();

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "books",
                    "books.authors",
                    "books.categories",
                    "books.pictures"
            }
    )
    Optional<Library> findById(int id);
}
