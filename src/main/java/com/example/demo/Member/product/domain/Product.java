package com.example.demo.Member.product.domain;

import com.example.demo.Member.Auth.domain.Users;
import com.example.demo.Member.common.BaseEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "Product")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id; // 상품 ID

    @Column(name = "title", nullable = false)
    private String title; // 상품 제목

    @Column(name = "description")
    private String description; // 상품 설명

    @Column(name = "price", nullable = false)
    private BigDecimal price; // 판매 가액

    @Column(name = "views", columnDefinition = "INT DEFAULT 0")
    private int views = 0; // 조회수

    @Column(name = "status")
    private String status; // 상품 상태 (ex: NEW, USED)

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user; // 판매자 ID (User 테이블 참조)

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location; // 판매 지역 ID (Location 테이블 참조)

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category; // 카테고리 ID (Category 테이블 참조)
}
