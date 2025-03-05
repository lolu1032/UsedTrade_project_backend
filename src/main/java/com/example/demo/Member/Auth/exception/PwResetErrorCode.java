package com.example.demo.Member.Auth.exception;

import com.example.demo.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum PwResetErrorCode implements ErrorCode  {
    DEFAULT("아이디/비밀번호 찾기 오류",HttpStatus.INTERNAL_SERVER_ERROR),
    TOKEN_NOT_FOUNT("유효하지 않은 토큰입니다.",HttpStatus.NOT_FOUND),
    EXPIRED_TOKEN("만료된 토큰입니다. 다시 요청해주세요.", HttpStatus.UNAUTHORIZED);

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
