package com.example.demo.App.Chat.domain;

import com.example.demo.App.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    public static ChatRoomEntity create(String name) {
        return ChatRoomEntity.builder()
                .roomId(UUID.randomUUID().toString())
                .name(name)
                .build();
    }
}