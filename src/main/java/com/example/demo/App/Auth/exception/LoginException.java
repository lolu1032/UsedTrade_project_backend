package com.example.demo.App.Auth.exception;

import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;

public class LoginException extends CustomException {

    public LoginException() {
        super(LoginErrorCode.DEFAULT);
    }

    public LoginException(ErrorCode errorCode) {
        super(errorCode);
    }

    public LoginException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public LoginException(Throwable cause) {
        super(cause);
    }
}
