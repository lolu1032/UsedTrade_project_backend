package com.example.demo.App.Board.service;

import com.example.demo.App.Auth.domain.Users;
import com.example.demo.App.Auth.repository.UserRepository;
import com.example.demo.App.Board.domain.Category;
import com.example.demo.App.Board.domain.Likes;
import com.example.demo.App.Board.domain.Location;
import com.example.demo.App.Board.domain.Product;
import com.example.demo.App.Board.repository.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoardApiServiceTest {

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private UserRepository userRepository;

    @DisplayName("좋아요 테스트코드")
    @Test
    void likes() {
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

        // when 사용하는 이유 데이터베이스가 없어도 메소드가 실제 데이터베이스처럼 움직일 수 있게 도와줌
        when(boardRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(userRepository.findById(users.getId())).thenReturn(Optional.of(users));

        Product products = boardRepository.findById(product.getId())
                .orElseThrow();
        Users userss = userRepository.findById(users.getId())
                .orElseThrow();

        Likes like = new Likes(false, userss, products);

        // when
        Likes updatedLike = Likes.builder()
                .status(true)
                .users(like.getUsers())
                .product(like.getProduct())
                .build();

        // then
        assertThat(updatedLike.getStatus()).isTrue();
    }
}
