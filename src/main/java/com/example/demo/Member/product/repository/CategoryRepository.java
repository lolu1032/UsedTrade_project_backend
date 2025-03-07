package com.example.demo.Member.product.repository;

import com.example.demo.Member.product.domain.Category;
import com.example.demo.Member.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
