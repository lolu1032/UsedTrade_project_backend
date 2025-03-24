package com.example.demo.App.Chat.domain;

import com.example.demo.App.Auth.domain.Users;
import com.example.demo.App.Board.domain.Product;
import com.example.demo.App.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "chat_rooms")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ChatRoomEntity extends BaseEntity {

    @Id
    private String roomId;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users userId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product productId;

}