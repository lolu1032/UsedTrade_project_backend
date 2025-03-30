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
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class BoardApiServiceTest {

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private UserRepository userRepository;

    @DisplayName("좋아요 true 테스트")
    @Test
    void likeToTrue() {
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
        given(boardRepository.findById(product.getId())).willReturn(Optional.of(product));
        given(userRepository.findById(users.getId())).willReturn(Optional.of(users));

        Product products = boardRepository.findById(product.getId())
                .orElseThrow();
        Users userss = userRepository.findById(users.getId())
                .orElseThrow();

        Likes like = Likes.builder()
                .users(userss)
                .status(true)
                .product(products)
                .build();

        // when
        Likes updatedLike = Likes.builder()
                .status(like.getStatus())
                .users(like.getUsers())
                .product(like.getProduct())
                .build();

        // then
        assertThat(updatedLike.getStatus()).isTrue();
    }
    @DisplayName("좋아요 false 테스트")
    @Test
    void likeToFalse() {
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
        given(boardRepository.findById(product.getId())).willReturn(Optional.of(product));
        given(userRepository.findById(users.getId())).willReturn(Optional.of(users));

        Product products = boardRepository.findById(product.getId())
                .orElseThrow();
        Users userss = userRepository.findById(users.getId())
                .orElseThrow();

        Likes like = Likes.builder()
                        .users(userss)
                        .status(false)
                        .product(products)
                        .build();

        // when
        Likes updatedLike = Likes.builder()
                .status(like.getStatus())
                .users(like.getUsers())
                .product(like.getProduct())
                .build();

        // then
        assertThat(updatedLike.getStatus()).isFalse();
    }
}
