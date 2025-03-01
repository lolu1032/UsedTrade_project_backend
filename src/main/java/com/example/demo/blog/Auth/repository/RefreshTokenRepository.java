package com.example.demo.blog.Auth.repository;

import com.example.demo.blog.Auth.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findById(Long userId);
}
