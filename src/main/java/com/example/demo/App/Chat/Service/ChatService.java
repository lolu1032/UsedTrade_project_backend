package com.example.demo.App.Chat.Service;

import com.example.demo.App.Auth.domain.Users;
import com.example.demo.App.Auth.repository.UserRepository;
import com.example.demo.App.Board.domain.Product;
import com.example.demo.App.Board.repository.BoardRepository;
import com.example.demo.App.Chat.Repository.ChatRoomRepository;
import com.example.demo.App.Chat.domain.ChatRoomEntity;
import com.example.demo.App.Chat.dto.ChatCommandDtos.*;
import com.example.demo.App.Chat.dto.ChatMessage;
import com.example.demo.App.Chat.dto.ChatRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final SimpMessageSendingOperations messagingTemplate;

    private final ChatRoomRepository chatRoomRepository;

    private final BoardRepository boardRepository;

    private final UserRepository userRepository;

    // 채팅방 ID와 채팅방 객체를 저장하는 맵
    private Map<String, ChatRoom> chatRooms = new ConcurrentHashMap<>();

    public ChatRoomEntity createRoom(ChatRoomRequest request) {
        ChatRoomRequest requests = ChatRoomRequest.builder()
                .userId(request.userId())
                .productId(request.productId())
                .name(request.name())
                .build();

        Users userId = userRepository.findById(requests.userId()).orElseThrow();

        Product productId = boardRepository.findById(requests.productId()).orElseThrow();

        // TODO 이쪽 부근 HTTP Status 설정해서 프론트 단에서 이 상태코드 응답 받으면 방생성말고 방리스트쪽으로 옮기라는 식으로 진행
        if(chatRoomRepository.existsByUserIdAndProductId(userId,productId)) {
            throw new IllegalArgumentException("방생성 못해요");
        }

        ChatRoomEntity chatRoomEntity = ChatRoom.create(requests, userId, productId);


        return chatRoomRepository.save(chatRoomEntity);

    }

    // 모든 채팅방 조회
    public List<ChatRoomEntity> findAllRooms() {
        return chatRoomRepository.findAll();
    }

    // 특정 채팅방 조회
    public ChatRoom findRoomById(String roomId) {
        return chatRooms.get(roomId);
    }

    // 채팅방 삭제
    public void deleteRoom(String roomId) {
        chatRooms.remove(roomId);
    }

    // 메시지 발송
    public void sendMessage(ChatMessage message) {
        log.info("메시지 발송: {}", message);
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);

        // 마지막 메시지 업데이트 (채팅방 목록에서 표시용)
        if (message.getType() == ChatMessage.MessageType.TALK) {
            ChatRoom room = chatRooms.get(message.getRoomId());
            if (room != null) {
                // 기존 room에서 새로운 ChatRoom 객체로 교체 (불변 설계)
                ChatRoom updatedRoom = ChatRoom.builder()
                        .roomId(room.getRoomId())
                        .name(room.getName())
                        .lastMessage(message.getMessage())
                        .build();

                chatRooms.put(message.getRoomId(), updatedRoom);
            }
        }
    }

}