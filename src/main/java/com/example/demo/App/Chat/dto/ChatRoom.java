package com.example.demo.App.Chat.dto;

import lombok.Getter;

import java.util.UUID;

import lombok.Builder;

@Getter
public class ChatRoom {
    private String roomId;
    private String name;
    private String lastMessage; // React 연동 고려 필드

    @Builder
    public ChatRoom(String roomId, String name, String lastMessage) {
        this.roomId = roomId;
        this.name = name;
        this.lastMessage = lastMessage;
    }

    public static ChatRoom create(String name) {
        return ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .name(name)
                .build();
    }
}
