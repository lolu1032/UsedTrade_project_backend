package com.example.demo.App.Auth.controller;

import com.example.demo.App.Auth.dto.LoginCommandDtos.*;
import com.example.demo.App.Auth.service.UserService;
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
    public AccessTokenResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        return service.login(loginRequest);
    }

    @PostMapping("/signup")
    public TokensResponse sign(@RequestBody @Valid PasswordRequest passwordRequest) {
        return service.sign(passwordRequest);
    }
}
