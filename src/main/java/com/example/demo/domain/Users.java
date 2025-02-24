package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // SERIAL 필드 자동 생성
    private Long id;
    private String password;
}