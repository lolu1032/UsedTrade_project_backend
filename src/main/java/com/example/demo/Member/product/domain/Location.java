package com.example.demo.Member.product.domain;

import jakarta.persistence.*;


@Entity
@Table(name = "Location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 지역 ID

    @Column(name = "latitude", nullable = false)
    private double latitude; // 위도

    @Column(name = "longitude", nullable = false)
    private double longitude; // 경도

    @Column(name = "region_name")
    private String regionName; // 지역명 (예: '서울시 강남구')

    @Column(name = "full_address")
    private String fullAddress; // 구체적인 주소
}
