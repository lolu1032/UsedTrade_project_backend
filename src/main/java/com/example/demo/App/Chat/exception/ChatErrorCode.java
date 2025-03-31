package com.example.demo.App.Chat.exception;

import com.example.demo.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum ChatErrorCode implements ErrorCode {
    DEFAULT("서버 오류", HttpStatus.INTERNAL_SERVER_ERROR),
    CREATED_CHAT_ROOM("이미 방이 생성되어있습니다.",HttpStatus.CONFLICT),
    NOT_FOUND_CHAT_ROOM("방을 찾을 수 없습니다.",HttpStatus.NOT_FOUND);

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
        return new ChatException(this);
    }

    @Override
    public RuntimeException exception(Throwable cause) {
        return new ChatException(cause);
    }
}
