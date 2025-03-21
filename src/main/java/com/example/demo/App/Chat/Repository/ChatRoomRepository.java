package com.example.demo.App.Chat.Repository;


import com.example.demo.App.Chat.domain.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, String> {
    List<ChatRoomEntity> findAllByOrderByNameAsc();
}