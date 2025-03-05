package com.example.demo.Member.Auth.dto;

import lombok.Builder;

public class PasswordResetDtos {

    public record PasswordResetRequest(String email) {}

    public record PasswordUpdateRequest(String token, String newPassword) {}

    @Builder
    public record PasswordResetResponse(String message) {}
}