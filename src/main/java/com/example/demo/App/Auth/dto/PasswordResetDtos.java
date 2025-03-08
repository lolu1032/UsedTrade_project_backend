package com.example.demo.App.Auth.dto;

import lombok.Builder;

public class PasswordResetDtos {

    public record PasswordResetRequest(String email) {}

    public record PasswordUpdateRequest(String token, String newPassword) {}

    @Builder
    public record PasswordResetResponse(String message) {}
}