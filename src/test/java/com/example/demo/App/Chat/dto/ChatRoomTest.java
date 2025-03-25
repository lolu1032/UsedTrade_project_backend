package com.example.demo.App.Chat.dto;

import com.example.demo.App.Auth.domain.Users;
import com.example.demo.App.Auth.repository.UserRepository;
import com.example.demo.App.Board.domain.Product;
import com.example.demo.App.Board.repository.BoardRepository;
import com.example.demo.App.Chat.Repository.ChatRoomRepository;
import com.example.demo.App.Chat.Service.ChatService;
import com.example.demo.App.Chat.domain.ChatRoomEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import com.example.demo.App.Chat.dto.ChatCommandDtos.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class ChatRoomTest {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ChatService chatService;

    @DisplayName("방 생성 테스트")
    @Test
    void createRoom() {
        // given
        Users user = userRepository.findById(1L).orElseThrow();
        Product product = boardRepository.findById(1L).orElseThrow();

        Users user2 = userRepository.findById(2L).orElseThrow();
        Product product2 = boardRepository.findById(2L).orElseThrow();

        ChatRoomRequest request = ChatRoomRequest.builder()
                .userId(user.getId())
                .productId(product.getId())
                .name("테스트 채팅방")
                .build();

        ChatRoomRequest request2 = ChatRoomRequest.builder()
                .userId(user2.getId())
                .productId(product2.getId())
                .name("테스트 채팅방2")
                .build();

        ChatRoomEntity chatRoom = ChatRoom.create(request,user,product);
        ChatRoomEntity chatRoom2 = ChatRoom.create(request2, user2,product2);

        chatRoomRepository.save(chatRoom);
        chatRoomRepository.save(chatRoom2);

        // when
        var chatRooms = chatRoomRepository.findAll();

        // then
        assertThat(chatRooms).hasSize(2);
        assertThat(chatRooms.get(0).getProductId().getId()).isEqualTo(1L);
        assertThat(chatRooms.get(0).getName()).isEqualTo("테스트 채팅방");
    }
}
