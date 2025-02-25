package com.example.demo.blog.service;

import com.example.demo.blog.domain.Users;
import com.example.demo.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public Optional<Users> login(String email) {
        return userRepository.findByEmail(email);
    }
}
