package com.example.demo.App.Auth.repository;

import com.example.demo.App.Auth.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByEmail(String Email);

    Boolean existsByEmail(String email);

    boolean existsByPassword(String password);
}