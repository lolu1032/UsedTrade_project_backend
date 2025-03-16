package com.example.demo.App.Auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

public final class LoginCommandDtos {
    @Builder
    public record PasswordRequest(
            @NotBlank(message = "이메일을 입력하십시오.")
            @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", message = "올바른 이메일 형식이 아닙니다.")
            String email,
            @NotBlank(message = "비밀번호를 입력하십시오.")
            @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "비밀번호는 최소 8자 이상이어야 하며, 문자와 숫자가 포함되어야 합니다.")
            String password,
            @NotBlank(message = "이름을 입력하시오.")
            String username
    ){}
    @Builder
    public record LoginRequest(
            String email,
            String password
    ){}
    @Builder
    public record TokensResponse(
            String accessToken,
            String refreshToken
    ){}
    @Builder
    public record AccessTokenResponse(
            String accessToken,
            String username
    ){}
}
