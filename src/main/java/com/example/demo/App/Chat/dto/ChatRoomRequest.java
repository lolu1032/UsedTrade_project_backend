package com.example.demo.App.Chat.dto;

import com.example.demo.App.Auth.domain.Users;
import com.example.demo.App.Board.domain.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatRoomRequest {

    private String name;

    private Users userId;

    private Product productId;

    @Builder
    public ChatRoomRequest(String name, Users userId, Product productId) {
        this.name = name;
        this.userId = userId;
        this.productId = productId;
    }
}
