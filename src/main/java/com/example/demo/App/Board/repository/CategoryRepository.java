package com.example.demo.App.Board.repository;

import com.example.demo.App.Board.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
