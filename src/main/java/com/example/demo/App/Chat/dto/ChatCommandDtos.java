package com.example.demo.App.Chat.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public final class ChatCommandDtos {

    @Builder
    public record ChatRoomRequest(
            String name,
            Long userId,
            Long productId
    ){}

    @Builder
    public record ChatRoomResponse(HttpStatus status, String message,String roomId,String name){}
}

