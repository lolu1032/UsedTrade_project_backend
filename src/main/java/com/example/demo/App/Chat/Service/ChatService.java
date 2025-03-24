package com.example.demo.App.Chat.Service;

import com.example.demo.App.Chat.Repository.ChatRoomRepository;
import com.example.demo.App.Chat.domain.ChatRoomEntity;
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

    private final ChatRoomRepository chatRoomRepository;

    // 채팅방 ID와 채팅방 객체를 저장하는 맵
    private Map<String, ChatRoom> chatRooms = new ConcurrentHashMap<>();

    /**
     * TODO
     * 생성하기전 이미 만들어진 방이 있으면 안만들기.
     */
//    public ChatRoom createRoom(String name) {
//        log.info(name);
//        ChatRoom chatRoom = ChatRoom.create(name);
//        chatRooms.put(chatRoom.getRoomId(), chatRoom);
//        ChatRoomEntity chatRoomEntity = ChatRoomEntity.builder()
//                .roomId(chatRoom.getRoomId())
//                .name(chatRoom.getName())
//                .build();
//        chatRoomRepository.save(chatRoomEntity);
//        log.info("채팅방 생성 완료: {}", chatRoom);
//        return chatRoom;
//    }

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