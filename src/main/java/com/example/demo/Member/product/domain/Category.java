package com.example.demo.Member.product.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "Category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 카테고리 ID

    @Column(name = "category_name", nullable = false)
    private String categoryName; // 카테고리 이름 (예: 전자기기, 의류, 가구 등)
}