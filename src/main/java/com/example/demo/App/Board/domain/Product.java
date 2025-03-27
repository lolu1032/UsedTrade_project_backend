package com.example.demo.App.Board.domain;

import com.example.demo.App.Auth.domain.Users;
import com.example.demo.App.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

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

    private String status; // 상품 상태

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private Users user;

    @ManyToOne()
    @JoinColumn(name = "location_id")
    @JsonIgnore
    private Location location;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;


    @OneToMany(mappedBy = "product")
    @BatchSize(size = 4)
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
