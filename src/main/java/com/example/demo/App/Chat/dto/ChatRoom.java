package com.example.demo.App.Chat.dto;

import com.example.demo.App.Chat.domain.ChatRoomEntity;
import lombok.Getter;

import java.util.List;
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



    public static List<ChatRoomEntity> create(List<ChatRoomRequest> requests) {
        return requests.stream()
                .map(request -> ChatRoomEntity.builder()
                        .roomId(UUID.randomUUID().toString())
                        .name(request.getName())
                        .productId(request.getProductId())
                        .userId(request.getUserId())
                        .build()
                )
                .toList();
    }
}
