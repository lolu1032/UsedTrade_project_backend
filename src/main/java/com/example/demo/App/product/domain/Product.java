package com.example.demo.App.product.domain;

import com.example.demo.App.Auth.domain.Users;
import com.example.demo.App.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 상품 ID

    private String title; // 상품 제목

    private String description; // 상품 설명

    private BigDecimal price; // 판매 가액

    private int views; // 조회수

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


    @OneToMany(mappedBy = "product")
    private final List<Image> images = new ArrayList<>();

    public String getFirstImageUrl() {
        return images.isEmpty() ? null : images.get(0).getImageUrl();
    }

    public void update(String title, String description, BigDecimal price) {
        this.title = title;
        this.description = description;
        this.price = price;
    }

}
