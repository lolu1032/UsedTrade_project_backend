package com.example.demo.App.Chat.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber {

    private final SimpMessageSendingOperations messagingTemplate;

    // Redis에서 메시지 수신 시 호출
    public void handleMessage(String message) {
        log.info("Redis Subscriber 수신: {}", message);
        // 받은 메시지를 웹소켓 구독자들에게 전달
        messagingTemplate.convertAndSend("/sub/chat/room", message);
    }
}
