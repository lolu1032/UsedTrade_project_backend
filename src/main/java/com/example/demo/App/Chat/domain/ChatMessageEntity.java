package com.example.demo.App.Chat.domain;

import com.example.demo.App.Chat.dto.ChatMessage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomId;
    private String sender;
    private String message;

    @Enumerated(EnumType.STRING)
    private ChatMessage.MessageType type;

    private LocalDateTime sentAt;
}
