package com.example.demo.App.Board.repository;

import com.example.demo.App.Board.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BoardSearchRepository extends JpaRepository<Product,Long>, JpaSpecificationExecutor {
    @EntityGraph(attributePaths = {"location", "user", "category", "images"})
    Page<Product> findAll(Specification search, Pageable pageable);
}
