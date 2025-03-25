package com.example.demo.App.Chat.dto;

import com.example.demo.App.Auth.domain.Users;
import com.example.demo.App.Board.domain.Product;
import com.example.demo.App.Chat.domain.ChatRoomEntity;
import lombok.Getter;

import java.util.UUID;

import lombok.Builder;

@Getter
public class ChatRoom {
    private String roomId;
    private String name;
    private String lastMessage; // React 연동 고려 필드
    private Long userId;
    private Long productId;

    @Builder
    public ChatRoom(String roomId, String name, String lastMessage, Long userId, Long productId) {
        this.roomId = roomId;
        this.name = name;
        this.lastMessage = lastMessage;
    }


    public static ChatRoomEntity create(ChatCommandDtos.ChatRoomRequest requests , Users user, Product product) {
        return ChatRoomEntity.builder()
                .roomId(UUID.randomUUID().toString())
                .name(requests.name())
                .productId(product)
                .userId(user)
                .build();
    }
}
