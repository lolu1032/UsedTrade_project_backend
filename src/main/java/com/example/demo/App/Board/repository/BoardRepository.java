package com.example.demo.App.Board.repository;

import com.example.demo.App.Board.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Product,Long> {
    Page<Product> findAll(Pageable pageable); // 페이징 처리
}
