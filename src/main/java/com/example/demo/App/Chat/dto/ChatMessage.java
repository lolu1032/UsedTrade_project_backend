package com.example.demo.App.Chat.dto;

import lombok.Getter;

import lombok.Builder;

@Getter
public class ChatMessage {

    public enum MessageType {
        ENTER, TALK, EXIT, MATCH, MATCH_REQUEST;
    }
    private Long senderId;
    private Long productId;
    private String roomName;
    private MessageType type;
    private String roomId;
    private String sender;
    private String message;

    @Builder
    public ChatMessage(MessageType type, String roomId, String sender, String message) {
        this.type = type;
        this.roomId = roomId;
        this.sender = sender;
        this.message = message;
    }
}
