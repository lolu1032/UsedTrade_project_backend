package com.example.demo.App.Chat.Service;

import com.example.demo.App.Auth.domain.Users;
import com.example.demo.App.Auth.exception.LoginErrorCode;
import com.example.demo.App.Auth.repository.UserRepository;
import com.example.demo.App.Board.domain.Product;
import com.example.demo.App.Board.exception.BoardErrorCode;
import com.example.demo.App.Board.repository.BoardRepository;
import com.example.demo.App.Chat.Repository.ChatMessageRepository;
import com.example.demo.App.Chat.Repository.ChatRoomRepository;
import com.example.demo.App.Chat.domain.ChatMessageEntity;
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


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final SimpMessageSendingOperations messagingTemplate;

    private final ChatRoomRepository chatRoomRepository;

    private final BoardRepository boardRepository;

    private final UserRepository userRepository;

    private final ChatMessageRepository chatMessageRepository;

    // 채팅방 ID와 채팅방 객체를 저장하는 맵
    private Map<String, ChatRoom> chatRooms = new ConcurrentHashMap<>();

    public ChatRoomResponse createRoom(ChatRoomRequest request) {
        Users user = userRepository.findById(request.userId())
                .orElseThrow(LoginErrorCode.ID_NOT_FOUNT::exception);

        Product product = boardRepository.findById(request.productId())
                .orElseThrow(BoardErrorCode.BOARD_NOT_FOUND::exception);

        // 이미 생성된 방이면 알려주기만 (이때는 DB를 조회해야 함)
        if (chatRoomRepository.existsByUserIdAndProductId(user, product)) {
            return ChatRoomResponse.builder()
                    .status(ChatErrorCode.CREATED_CHAT_ROOM.status())
                    .message(ChatErrorCode.CREATED_CHAT_ROOM.message())
                    .build();
        }

        String roomId = UUID.randomUUID().toString();
        return ChatRoomResponse.builder()
                .roomId(roomId)
                .name(request.name())
                .message("임시 방 생성됨")
                .status(HttpStatus.OK)
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

        // 채팅방이 존재하지 않으면 이 시점에 생성
        if (!chatRoomRepository.existsById(message.getRoomId())) {
            // 생성자 정보와 상품 정보를 ChatMessage에 포함시켜야 함
            Users sender = userRepository.findById(message.getSenderId())
                    .orElseThrow(LoginErrorCode.ID_NOT_FOUNT::exception);

            Product product = boardRepository.findById(message.getProductId())
                    .orElseThrow(BoardErrorCode.BOARD_NOT_FOUND::exception);

            ChatRoomEntity chatRoomEntity = ChatRoomEntity.builder()
                    .roomId(message.getRoomId())
                    .name(product.getTitle() + " - " + sender.getUsername()) // 또는 message.getRoomName()
                    .userId(sender)
                    .productId(product)
                    .build();

            chatRoomRepository.save(chatRoomEntity);
        }

        // 메시지 저장 및 전송은 그대로
        ChatMessageEntity chatMessageEntity = ChatMessageEntity.builder()
                .roomId(message.getRoomId())
                .sender(message.getSender())
                .message(message.getMessage())
                .type(message.getType())
                .sentAt(LocalDateTime.now())
                .build();

        chatMessageRepository.save(chatMessageEntity);
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}