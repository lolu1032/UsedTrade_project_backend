package com.example.demo.Member.Auth.repository;

import com.example.demo.Member.Auth.domain.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);
    Optional<VerificationToken> findByUserId(Long userId);
    void deleteByExpiryDateBefore(Instant expiryDate);
}