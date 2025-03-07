package com.example.demo.Member.product.repository;

import com.example.demo.Member.product.domain.Product;
import com.example.demo.Member.product.dto.BoardReadDtos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Product,Long> {
    Page<Product> findAll(Pageable pageable); // 페이징 처리
}
