package com.example.demo.App.Chat.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public final class ChatCommandDtos {

    @Builder
    public record ChatRoomRequest(
            String name,
            Long userId,
            Long productId
    ){}
}

