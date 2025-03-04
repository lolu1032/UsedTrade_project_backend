package com.example.demo.blog.Auth.controller;

import com.example.demo.blog.Auth.dto.PasswordResetDtos.*;
import com.example.demo.blog.Auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class PasswordResetController {
    private final UserService userService;

    @PostMapping("/password-reset-request")
    public ResponseEntity<PasswordResetResponse> requestPasswordReset(@RequestBody PasswordResetRequest request) {
        return ResponseEntity.ok(userService.requestPasswordReset(request));
    }

    @PostMapping("/password-reset")
    public ResponseEntity<PasswordResetResponse> resetPassword(@RequestBody PasswordUpdateRequest request) {
        return ResponseEntity.ok(userService.resetPassword(request));
    }
}