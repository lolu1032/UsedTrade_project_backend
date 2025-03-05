package com.example.demo.Member.Auth.domain;

import com.example.demo.Member.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public boolean matchPassword(BCryptPasswordEncoder encoder , String password) {
        return !encoder.matches(password,this.password);
    }
    public Long fetchUserId() {
        return this.id;
    }
    public String fetchUserEmail() {
        return this.email;
    }
}