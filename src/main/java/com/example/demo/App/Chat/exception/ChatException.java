package com.example.demo.App.Chat.exception;

import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;

public class ChatException extends CustomException {
    public ChatException() {
        super(ChatErrorCode.DEFAULT);
    }

    public ChatException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ChatException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public ChatException(Throwable cause) {
        super(cause);
    }
}
