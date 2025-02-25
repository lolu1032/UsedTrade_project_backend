package com.example.demo.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // ResponseEntity -> 제네릭에 와일드카드
    // ResponseEntity<ApiError>
    @ExceptionHandler(CustomException.class) // 컨트룰러에서까지 캐치하지 않으면 여기로
    public ResponseEntity<ApiError> handleCustomException(CustomException exception, HttpServletRequest request) {
        // 응답을 교체할 수 있음
        var errorCode = exception.getErrorCode();
        var path = request.getRequestURI(); // ex) "/boards"

        ApiError error = ApiError.builder()
                .title(errorCode.message())
                .status(errorCode.status().value())
//                .detail()
                .instance(path)
                .build();
        // 동적으로 응답의 HTTP 상태 코드를 바꿀 수 잇다.
//        return new ResponseEntity<>(error, errorCode.status());
        return ResponseEntity
                .status(errorCode.status())
                .body(error);
    }
}
