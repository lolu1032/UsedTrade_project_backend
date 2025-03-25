package com.example.demo.App.Chat.Controller;

import com.example.demo.App.Chat.Service.ChatService;
import com.example.demo.App.Chat.domain.ChatRoomEntity;
import com.example.demo.App.Chat.dto.ChatCommandDtos.*;
import com.example.demo.App.Chat.dto.ChatMessage;
import com.example.demo.App.Chat.dto.ChatRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatService chatService;

    // 채팅방 생성
    @PostMapping("/room")
    public ChatRoomEntity createRoom(@RequestBody ChatRoomRequest request) {
        log.info("방 생성 요청: {}", request.name());
        return chatService.createRoom(request);
    }

    // 채팅방 목록 조회
    @GetMapping("/rooms")
    public List<ChatRoomEntity> getRoomList() {
        log.info("방 목록 조회 요청");
        return chatService.findAllRooms();
    }

    // 채팅방 정보 조회
    @GetMapping("/room/{roomId}")
    public ChatRoom getRoomInfo(@PathVariable String roomId) {
        log.info("방 정보 조회 요청: {}", roomId);
        return chatService.findRoomById(roomId);
    }

    // 채팅방 삭제
    @DeleteMapping("/room/{roomId}")
    public void deleteRoom(@PathVariable String roomId) {
        log.info("방 삭제 요청: {}", roomId);
        chatService.deleteRoom(roomId);
    }

    // 채팅 메시지 전송
    @MessageMapping("/chat/message")
    public void sendMessage(@Payload ChatMessage chatMessage) {
        log.info("메시지 전송 요청: {}", chatMessage);

        ChatMessage finalMessage = chatMessage;

        // 메시지 타입에 따른 메시지 생성
        if (chatMessage.getType() == ChatMessage.MessageType.ENTER) {
            finalMessage = ChatMessage.builder()
                    .type(ChatMessage.MessageType.ENTER)
                    .roomId(chatMessage.getRoomId())
                    .sender(chatMessage.getSender())
                    .message(chatMessage.getSender() + "님이 입장하셨습니다.")
                    .build();
        } else if (chatMessage.getType() == ChatMessage.MessageType.EXIT) {
            finalMessage = ChatMessage.builder()
                    .type(ChatMessage.MessageType.EXIT)
                    .roomId(chatMessage.getRoomId())
                    .sender(chatMessage.getSender())
                    .message(chatMessage.getSender() + "님이 퇴장하셨습니다.")
                    .build();
        }

        // Redis pub/sub을 통해 메시지 발행
        chatService.sendMessage(finalMessage);
    }

    // 사용자 입장 처리
    @MessageMapping("/chat/enter")
    public void enterUser(@Payload ChatMessage message, SimpMessageHeaderAccessor headerAccessor) {
        log.info("사용자 입장: {}", message);

        // 웹소켓 세션에 사용자 정보 저장
        headerAccessor.getSessionAttributes().put("username", message.getSender());
        headerAccessor.getSessionAttributes().put("roomId", message.getRoomId());

        ChatMessage enterMessage = ChatMessage.builder()
                .type(ChatMessage.MessageType.ENTER)
                .roomId(message.getRoomId())
                .sender(message.getSender())
                .message(message.getSender() + "님이 입장하셨습니다.")
                .build();

        chatService.sendMessage(enterMessage);
    }

}