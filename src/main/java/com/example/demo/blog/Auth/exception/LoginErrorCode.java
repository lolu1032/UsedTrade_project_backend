package com.example.demo.blog.Auth.exception;

import com.example.demo.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum LoginErrorCode implements ErrorCode {
    DEFAULT("로그인 오류",HttpStatus.INTERNAL_SERVER_ERROR),
    EMAIL_NOT_FOUND("이메일이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    PASSWORD_NOT_FOUND("비밀번호가 틀렸습니다.", HttpStatus.NOT_FOUND),
    USERNAME_ALREADY_EXISTS("이미 사용 중인 계정입니다.",HttpStatus.CONFLICT),
    TOKEN_NOT_FOUND("토큰이 존재하지 않습니다.",HttpStatus.NOT_FOUND);
    private final String message;
    private final HttpStatus status;

    @Override
    public String message() {
        return message;
    }

    @Override
    public HttpStatus status() {
        return status;
    }

    @Override
    public RuntimeException exception() {
        return new LoginException(this);
    }

    @Override
    public RuntimeException exception(Throwable cause) {
        return new LoginException(cause);
    }
}
