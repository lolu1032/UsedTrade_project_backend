package com.example.demo.App.product.exception;

import com.example.demo.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum BoardErrorCode implements ErrorCode {
    DEFAULT("서버 오류",HttpStatus.INTERNAL_SERVER_ERROR),
    BOARD_NOT_FOUND("해당 페이지가 존재하지 않습니다.", HttpStatus.NOT_FOUND);
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
        return new BoardException(this);
    }

    @Override
    public RuntimeException exception(Throwable cause) {
        return new BoardException(cause);
    }
}
