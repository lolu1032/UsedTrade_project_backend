package com.example.demo.App.Board.repository;

import com.example.demo.App.Board.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Product,Long> {
    @EntityGraph(attributePaths = {"category", "location", "user"})
    Page<Product> findAll(Pageable pageable); // 페이징 처리
    List<Product> findByUserId(Long userId);
}
