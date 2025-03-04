package com.example.demo.blog.Auth.domain;

import com.example.demo.blog.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Users extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // SERIAL 필드 자동 생성
    private Long id;
    private String email;
    private String password;
    private String username;

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}