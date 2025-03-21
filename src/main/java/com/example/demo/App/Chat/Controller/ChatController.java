package com.example.demo.App.Chat.Controller;

import com.example.demo.App.Chat.Service.ChatService;
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
    public ChatRoom createRoom(@RequestBody ChatRoom chatRoom) {
        log.info("방 생성 요청: {}", chatRoom.getName());
        return chatService.createRoom(chatRoom.getName());
    }

    // 채팅방 목록 조회
    @GetMapping("/rooms")
    public List<ChatRoom> getRoomList() {
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

        // 메시지 타입에 따른 처리
        switch (chatMessage.getType()) {
            case ENTER:
                chatMessage.setMessage(chatMessage.getSender() + "님이 입장하셨습니다.");
                break;
            case EXIT:
                chatMessage.setMessage(chatMessage.getSender() + "님이 퇴장하셨습니다.");
                break;
            default:
                break;
        }

        // Redis pub/sub을 통해 메시지 발행
        chatService.sendMessage(chatMessage);
    }

    // 사용자 입장 처리
    @MessageMapping("/chat/enter")
    public void enterUser(@Payload ChatMessage message, SimpMessageHeaderAccessor headerAccessor) {
        log.info("사용자 입장: {}", message);

        // 웹소켓 세션에 사용자 정보 저장
        headerAccessor.getSessionAttributes().put("username", message.getSender());
        headerAccessor.getSessionAttributes().put("roomId", message.getRoomId());

        message.setType(ChatMessage.MessageType.ENTER);
        chatService.sendMessage(message);
    }
}