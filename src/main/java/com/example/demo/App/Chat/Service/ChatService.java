package com.example.demo.App.Chat.Service;

import com.example.demo.App.Chat.dto.ChatMessage;
import com.example.demo.App.Chat.dto.ChatRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final SimpMessageSendingOperations messagingTemplate;

    // 채팅방 ID와 채팅방 객체를 저장하는 맵
    private Map<String, ChatRoom> chatRooms = new ConcurrentHashMap<>();

    // 채팅방 생성
    public ChatRoom createRoom(String name) {
        ChatRoom chatRoom = ChatRoom.create(name);
        chatRooms.put(chatRoom.getRoomId(), chatRoom);
        log.info("채팅방 생성 완료: {}", chatRoom);
        return chatRoom;
    }

    // 모든 채팅방 조회
    public List<ChatRoom> findAllRooms() {
        List<ChatRoom> result = new ArrayList<>(chatRooms.values());
        Collections.reverse(result); // 최신순으로 정렬
        return result;
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
                room.setLastMessage(message.getMessage());
            }
        }
    }
}