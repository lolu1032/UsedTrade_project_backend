package com.example.demo.App.Board.controller;

import com.example.demo.App.Auth.domain.Users;
import com.example.demo.App.Auth.repository.UserRepository;
import com.example.demo.App.Board.domain.Category;
import com.example.demo.App.Board.domain.Location;
import com.example.demo.App.Board.domain.Product;
import com.example.demo.App.Board.dto.BoardReadDtos.*;
import com.example.demo.App.Board.repository.BoardRepository;
import com.example.demo.App.Board.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class BoardControllerTest {

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BoardService boardService;

    @DisplayName("게시글 생성")
    @Test
    void createBoard() {
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

        Users users = Users.builder()
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
                .user(users)
                .location(location)
                .category(category)
                .build();

        when(boardRepository.save(product)).thenReturn(product);
        // when
        Product products = boardRepository.save(product);
        // then
        assertThat(products.getTitle()).isEqualTo("zz");
        assertThat(products.getDescription()).isEqualTo("213");
        assertThat(products.getCategory()).isEqualTo(category);
    }

    @DisplayName("게시글 수정")
    @Test
    void updateBoard() {
        // given
        Long id = 1L;
        Product product = Product.builder()
                .id(id)
                .title("titleee")
                .description("zzzzzz")
                .price(BigDecimal.valueOf(100000))
                .build();

        String updateTitle = "New Title";
        String updateDescription = "New Description";

        UpdateBoardRequest request = new UpdateBoardRequest(id,updateTitle, updateDescription, BigDecimal.valueOf(0));

        given(boardService.updateBoard(id, request))
                .willReturn(new UpdateBoardResponse(id, updateTitle, updateDescription, BigDecimal.valueOf(0)));

        // when
        UpdateBoardResponse response = boardService.updateBoard(id,request);

        // then
        assertThat(response.title()).isEqualTo("New Title");
        assertThat(response.description()).isEqualTo("New Description");

    }

}