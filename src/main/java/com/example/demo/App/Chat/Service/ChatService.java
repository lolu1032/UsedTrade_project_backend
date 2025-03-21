package com.example.demo.App.Chat.Service;
import com.example.demo.App.Chat.Repository.ChatRoomRepository;
import com.example.demo.App.Chat.domain.ChatRoomEntity;
import com.example.demo.App.Chat.dto.ChatMessage;
import com.example.demo.App.Chat.dto.ChatRoom;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ChatRoomRepository chatRoomRepository;

    // DTO와 Entity 간 변환 메서드
    private ChatRoom convertToDto(ChatRoomEntity entity) {
        ChatRoom dto = new ChatRoom();
        dto.setRoomId(entity.getRoomId());
        dto.setName(entity.getName());
        return dto;
    }

    // 채팅방 생성
    @Transactional
    public ChatRoom createRoom(String name) {
        ChatRoomEntity chatRoomEntity = ChatRoomEntity.create(name);
        chatRoomEntity = chatRoomRepository.save(chatRoomEntity);
        return convertToDto(chatRoomEntity);
    }

    // 모든 채팅방 조회
    @Transactional(readOnly = true)
    public List<ChatRoom> findAllRooms() {
        return chatRoomRepository.findAllByOrderByNameAsc().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 특정 채팅방 조회
    @Transactional(readOnly = true)
    public ChatRoom findRoomById(String roomId) {
        return chatRoomRepository.findById(roomId)
                .map(this::convertToDto)
                .orElse(null);
    }

    // 채팅방 삭제
    @Transactional
    public void deleteRoom(String roomId) {
        chatRoomRepository.deleteById(roomId);
    }

    // 메시지 발행 (Redis로 발행)
    public void sendMessage(ChatMessage chatMessage) {
        try {
            String topic = "chat-room-" + chatMessage.getRoomId();
            log.info("메시지 발행: {}, 토픽: {}", chatMessage, topic);

            // Redis의 pub/sub 기능을 사용하여 메시지 발행
            redisTemplate.convertAndSend(topic, chatMessage);
        } catch (Exception e) {
            log.error("메시지 발행 중 오류 발생", e);
        }
    }
}