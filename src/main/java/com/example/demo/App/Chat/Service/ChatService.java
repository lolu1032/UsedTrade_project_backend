package com.example.demo.App.Chat.Service;

import com.example.demo.App.Auth.domain.Users;
import com.example.demo.App.Auth.exception.LoginErrorCode;
import com.example.demo.App.Auth.repository.UserRepository;
import com.example.demo.App.Board.domain.Product;
import com.example.demo.App.Board.exception.BoardErrorCode;
import com.example.demo.App.Board.repository.BoardRepository;
import com.example.demo.App.Chat.Repository.ChatRoomRepository;
import com.example.demo.App.Chat.domain.ChatRoomEntity;
import com.example.demo.App.Chat.dto.ChatCommandDtos.*;
import com.example.demo.App.Chat.dto.ChatMessage;
import com.example.demo.App.Chat.dto.ChatRoom;
import com.example.demo.App.Chat.exception.ChatErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
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

    public ChatRoomResponse createRoom(ChatRoomRequest request) {

        Users userId = userRepository.findById(request.userId())
                .orElseThrow(LoginErrorCode.ID_NOT_FOUNT::exception);

        Product productId = boardRepository.findById(request.productId())
                .orElseThrow(BoardErrorCode.BOARD_NOT_FOUND::exception);

        if (chatRoomRepository.existsByUserIdAndProductId(userId, productId)) {
            return ChatRoomResponse.builder()
                    .status(ChatErrorCode.CREATED_CHAT_ROOM.status())
                    .message(ChatErrorCode.CREATED_CHAT_ROOM.message())
                    .build();
        }

        ChatRoomEntity chatRoomEntity = ChatRoom.create(request, userId, productId);

        chatRoomRepository.save(chatRoomEntity);

        return ChatRoomResponse.builder()
                .status(HttpStatus.OK)
                .message("성공")
                .build();

    }

    // 특정 채팅방 조회
    public ChatRoomEntity findRoomById(String roomId) {
        return chatRoomRepository.findById(roomId)
                .orElseThrow(ChatErrorCode.NOT_FOUND_CHAT_ROOM::exception);
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