package com.facenet.bt2.repos;

import com.facenet.bt2.entity.Book;
import com.facenet.bt2.entity.Library;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepos extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"authors", "categories", "libraries", "pictures"})
    Page<Book> findAll(Specification<Book> specBook, Pageable pageable);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"authors", "categories", "libraries", "pictures"})
    Optional<Book> findById(int id);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"authors", "categories", "libraries", "pictures"})
    Page<Book> findAllByLibraries(Library library, Pageable pageable);
}
