package com.example.demo.App.Board.domain;

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
    private Long id;

    private String title;

    private String description;

    private BigDecimal price;

    private int views;

    /**
     * TODO
     * status용 enum을 추가로 만들 것
     * 판매중, 예약, 판매완료
     * SALE,RESERVED,SOLD
     */
    private String status; // 상품 상태

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


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
    public int updateViews() {
        return this.views++;
    }
}
