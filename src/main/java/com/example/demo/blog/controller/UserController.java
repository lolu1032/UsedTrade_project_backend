package com.example.demo.blog.controller;

import com.example.demo.blog.domain.Users;
import com.example.demo.blog.dto.LoginCommandDtos.*;
import com.example.demo.blog.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class UserController {
    private final UserService service;

    @PostMapping("/login")
    public LoginSelectResponse login(@RequestBody Users users) {
        return service.login(users);
    }

    @PostMapping("/signup")
    public Users sign(@RequestBody @Valid LoginSelectRequest loginSelectRequest) {
        return service.sign(loginSelectRequest);
    }
}
