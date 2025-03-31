package com.example.demo.App.Chat.dto;

import com.example.demo.App.Auth.domain.Users;
import com.example.demo.App.Auth.repository.UserRepository;
import com.example.demo.App.Board.domain.Category;
import com.example.demo.App.Board.domain.Location;
import com.example.demo.App.Board.domain.Product;
import com.example.demo.App.Board.repository.BoardRepository;
import com.example.demo.App.Chat.Repository.ChatRoomRepository;
import com.example.demo.App.Chat.Service.ChatService;
import com.example.demo.App.Chat.domain.ChatRoomEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import com.example.demo.App.Chat.dto.ChatCommandDtos.*;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ChatRoomTest {

    @Mock
    private ChatRoomRepository chatRoomRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private ChatService chatService;

    @DisplayName("방 생성 테스트")
    @Test
    void createRoom() {
        // given
        Location location = Location.builder()
                .id(1L)
                .longitude(3.0)
                .latitude(4.0)
                .regionName("123")
                .fullAddress("33")
                .build();
        Category category = Category.builder()
                .id(1L)
                .categoryName("zz")
                .build();

        Users user = Users.builder()
                .id(1L)
                .username("p")
                .email("qwer1234@naer.com")
                .password("qwer1234")
                .build();

        Product product = Product.builder()
                .id(1L)
                .title("zz")
                .description("213")
                .price(BigDecimal.valueOf(0))
                .status("new")
                .views(0)
                .user(user)
                .location(location)
                .category(category)
                .build();

        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(boardRepository.findById(1L)).willReturn(Optional.of(product));


        Users users = userRepository.findById(1L).orElseThrow();
        Product products = boardRepository.findById(1L).orElseThrow();

        ChatRoomRequest request = ChatRoomRequest.builder()
                .userId(users.getId())
                .productId(products.getId())
                .name("테스트 채팅방")
                .build();


        ChatRoomEntity chatRoom = ChatRoom.create(request,users,products);

        // when
        when(chatRoomRepository.save(chatRoom)).thenReturn(chatRoom);
        when(chatRoomRepository.findAll()).thenReturn(List.of(chatRoom));

        chatRoomRepository.save(chatRoom);
        var chatRooms = chatRoomRepository.findAll();

        // then
        assertThat(chatRooms.get(0).getProductId().getId()).isEqualTo(1L);
        assertThat(chatRooms.get(0).getName()).isEqualTo("테스트 채팅방");
    }
}
