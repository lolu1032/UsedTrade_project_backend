package com.example.demo.blog.controller;

import com.example.demo.blog.domain.Users;
import com.example.demo.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService service;

    @PostMapping("/login")
    public Optional<Users> login(@RequestBody String email) {
        return service.login(email);
    }
}
