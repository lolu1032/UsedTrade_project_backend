package com.example.demo.App.product.repository;

import com.example.demo.App.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Product,Long> {
    // 여러번 각각의 엔티티들을 조회 하여 한번에 조회할 수 있게 조치
    @EntityGraph(attributePaths = {"location", "user", "category", "images"})
    Page<Product> findAll(Pageable pageable); // 페이징 처리
}
