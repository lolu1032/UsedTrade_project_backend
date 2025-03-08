package com.example.demo.App.product.domain;

import jakarta.persistence.*;
import lombok.*;


@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 지역 ID

    private double latitude; // 위도

    private double longitude; // 경도

    private String regionName; // 지역명 (예: '서울시 강남구')

    private String fullAddress; // 구체적인 주소
}
