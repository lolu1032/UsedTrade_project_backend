package com.example.demo.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;

@Builder
public record ApiError(
        String type,
        @JsonInclude(Include.NON_NULL) // 기본 메시지 컨버터인 JackSon에서 반환 시 null이면 JSON에 포함하지 않게 함.
        String title,
        @JsonInclude(Include.NON_NULL)
        Integer status,
        @JsonInclude(Include.NON_NULL)
        String detail,
        @JsonInclude(Include.NON_NULL)
        String instance
) {
    public ApiError {
        if (type == null || type.isBlank()) {
            type = "about:blank";
        }
    }
}
