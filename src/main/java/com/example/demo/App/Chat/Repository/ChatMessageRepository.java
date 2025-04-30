package com.example.demo.App.Chat.Repository;

import com.example.demo.App.Chat.domain.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {
    List<ChatMessageEntity> findByRoomIdOrderBySentAtAsc(String roomId);
}