package com.example.demo.App.Chat.dto;


import lombok.Data;

import java.util.UUID;

@Data
public class ChatRoom {
    private String roomId;
    private String name;
    private String lastMessage; // This field exists in your React code

    public static ChatRoom create(String name) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.name = name; // This was missing in your code
        return chatRoom;
    }
}