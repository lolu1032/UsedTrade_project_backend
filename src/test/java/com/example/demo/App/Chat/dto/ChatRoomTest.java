package com.example.demo.App.Chat.dto;

import com.example.demo.App.Auth.domain.Users;
import com.example.demo.App.Auth.dto.LoginCommandDtos;
import com.example.demo.App.Auth.exception.LoginErrorCode;
import com.example.demo.App.Auth.exception.LoginException;
import com.example.demo.App.Auth.repository.UserRepository;
import com.example.demo.App.Board.domain.Product;
import com.example.demo.App.Board.repository.BoardRepository;
import com.example.demo.App.Chat.Repository.ChatRoomRepository;
import com.example.demo.App.Chat.Service.ChatService;
import com.example.demo.App.Chat.domain.ChatRoomEntity;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

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
    void test() {
        // given
        Users user = userRepository.findById(1L).orElseThrow();
        Product product = boardRepository.findById(1L).orElseThrow();

        Users user2 = userRepository.findById(2L).orElseThrow();
        Product product2 = boardRepository.findById(2L).orElseThrow();

        ChatRoomRequest request = ChatRoomRequest.builder()
                .userId(user)
                .productId(product)
                .name("테스트 채팅방")
                .build();

        ChatRoomRequest request2 = ChatRoomRequest.builder()
                .userId(user2)
                .productId(product2)
                .name("테스트 채팅방2")
                .build();

        List<ChatRoomEntity> chatRoom = ChatRoom.create(List.of(request, request2));

        List<ChatRoomEntity> save = chatRoomRepository.saveAll(chatRoom);

        // when
        var chatRooms = chatRoomRepository.findAll();

        // then
        assertThat(chatRooms).hasSize(2);
    }
}
