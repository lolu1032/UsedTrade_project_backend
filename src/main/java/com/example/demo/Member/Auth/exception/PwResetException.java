package com.example.demo.Member.Auth.exception;

import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;

public class PwResetException extends CustomException {
    public PwResetException() {
        super(PwResetErrorCode.DEFAULT);
    }
    public PwResetException(ErrorCode errorCode) {
        super(errorCode);
    }

    public PwResetException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public PwResetException(Throwable cause) {
        super(cause);
    }
}
