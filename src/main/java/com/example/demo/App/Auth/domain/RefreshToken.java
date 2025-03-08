package com.example.demo.App.Auth.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
public class RefreshToken {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @Builder
    public RefreshToken(String token, Users user) {
        this.token = token;
        this.user = user;
    }

    public void updateToken(String token) {
        this.token = token;
    }

    public String fetchToken() {
        return this.token;
    }
}