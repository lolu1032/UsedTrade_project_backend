package com.example.demo.App.Auth.service;

import com.example.demo.App.Auth.domain.Users;
import com.example.demo.App.Auth.dto.LoginCommandDtos.*;
import com.example.demo.App.Auth.exception.LoginErrorCode;
import com.example.demo.App.Auth.exception.LoginException;
import com.example.demo.App.Auth.repository.UserRepository;
import com.example.demo.App.Auth.util.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class) // 가짜 객체를 사용하기위해 선언
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserService userService;

    private JwtUtil jwtUtil;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @DisplayName("회원 생성")
    @Test
    void createUser() {
        // given
        PasswordRequest passwordRequest = new PasswordRequest("qwer1234@naver.com", "qwer1234", "p");

        String password = bCryptPasswordEncoder.encode(passwordRequest.password());

        Users users = Users.builder()
                .username(passwordRequest.username())
                .email(passwordRequest.email())
                .password(password)
                .build();

        // when

        // then
        assertThat(users.getEmail()).isEqualTo("qwer1234@naver.com");
        assertThat(users.getPassword()).isEqualTo(password);

    }

    @DisplayName("비밀번호 성공시 테스트")
    @Test
    void passwordTrue() {
        // given
        PasswordRequest passwordRequest = new PasswordRequest("qwer1234@naver.com", "qwer1234", "p");

        String password = bCryptPasswordEncoder.encode(passwordRequest.password());

        Users users = Users.builder()
                .username(passwordRequest.username())
                .email(passwordRequest.email())
                .password(password)
                .build();
        // when

        // then
        assertThat(bCryptPasswordEncoder.matches("qwer1234", users.getPassword())).isTrue();
    }

    @DisplayName("비밀번호 틀릴시 테스트")
    @Test
    void passwordFalse() {
        // given
        PasswordRequest passwordRequest = new PasswordRequest("qwer1234@naver.com", "qwer1234", "p");

        String password = bCryptPasswordEncoder.encode(passwordRequest.password());

        Users users = Users.builder()
                .username(passwordRequest.username())
                .email(passwordRequest.email())
                .password(password)
                .build();

        // when, then
        assertThat(bCryptPasswordEncoder.matches("qwer14234", users.getPassword())).isFalse();
        assertThatThrownBy(() -> {
            if (!bCryptPasswordEncoder.matches("qwer14234", users.getPassword())) {
                throw new LoginException(LoginErrorCode.PASSWORD_NOT_FOUND);
            }
        })
                .isInstanceOf(LoginException.class)
                .hasMessageContaining("비밀번호가 틀렸습니다.");
    }

    @DisplayName("로그인")
    @Test
    void login() {
        // given
        PasswordRequest passwordRequest = new PasswordRequest("qwer1234@naver.com", "qwer1234", "p");

        String password = bCryptPasswordEncoder.encode(passwordRequest.password());

        Users users = Users.builder()
                .username(passwordRequest.username())
                .email(passwordRequest.email())
                .password(password)
                .build();

        LoginRequest loginRequest = new LoginRequest("qwer1234@naver.com", "qwer1234");

        // when

        // then
        assertThat(users.getEmail()).isEqualTo(loginRequest.email());
        assertThat(bCryptPasswordEncoder.matches(loginRequest.password(), users.getPassword())).isTrue();
    }
}