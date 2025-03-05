package com.example.demo.Member.Auth.controller;

import com.example.demo.Member.Auth.dto.LoginCommandDtos.*;
import com.example.demo.Member.Auth.service.UserService;
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
    public accessTokenResponse login(@RequestBody @Valid LoginSelectResponse loginSelectResponse) {
        return service.login(loginSelectResponse);
    }

    @PostMapping("/signup")
    public TokensResponse sign(@RequestBody @Valid LoginSelectRequest loginSelectRequest) {
        return service.sign(loginSelectRequest);
    }
}
