package com.example.demo.App.Board.exception;

import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;

public class BoardException extends CustomException {

    public BoardException() {
        super(BoardErrorCode.DEFAULT);
    }

    public BoardException(ErrorCode errorCode) {
        super(errorCode);
    }

    public BoardException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public BoardException(Throwable cause) {
        super(cause);
    }
}
