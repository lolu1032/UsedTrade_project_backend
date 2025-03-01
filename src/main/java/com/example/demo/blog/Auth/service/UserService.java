package com.example.demo.blog.Auth.service;

import com.example.demo.blog.Auth.domain.RefreshToken;
import com.example.demo.blog.Auth.domain.Users;
import com.example.demo.blog.Auth.dto.LoginCommandDtos.*;
import com.example.demo.blog.Auth.exception.LoginErrorCode;
import com.example.demo.blog.Auth.repository.RefreshTokenRepository;
import com.example.demo.blog.Auth.repository.UserRepository;
import com.example.demo.blog.Auth.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;
    /**
     * 이메일이 없을 시 EMAIL_NOT_FOUND 예외 처리
     * 또는 이메일은 존재하지만 비밀번호가 틀릴 시 PASSWORD_NOT_FOUND 예외 처리
     * @param users
     * @return
     */
    public TokenResponse  login(Users users) {
        Users user = userRepository.findByEmail(users.getEmail())
            .orElseThrow(
                    LoginErrorCode.EMAIL_NOT_FOUND::exception
            );
        if(!userRepository.existsByPassword(user.getPassword())) {
            throw LoginErrorCode.PASSWORD_NOT_FOUND.exception();
        }
        RefreshToken refreshTokenEntity  = refreshTokenRepository.findById(user.getId())
                .orElseThrow(LoginErrorCode.TOKEN_NOT_FOUND::exception);
        String accessToken = "";
        String refreshToken = refreshTokenEntity.getToken();
        if (jwtUtil.isValidRefreshToken(refreshToken)) {
            accessToken = jwtUtil.createAccessToken(user);
        } else {
            refreshToken = jwtUtil.createRefreshToken(user);
            refreshTokenEntity.updateToken(refreshToken);
        }
        return TokenResponse.builder()
                .accessToken(accessToken)
                .build();

    }

    /**
     * Builder한 정보가 만약 있으면 USERNAME_ALREADY_EXISTS로 예외처리 없으면 저장
     * @param loginSelectRequest
     * @return
     */
    public TokenResponse sign(LoginSelectRequest loginSelectRequest) {
        Users user = Users.builder()
                .email(loginSelectRequest.email())
                .password(loginSelectRequest.password())
                .build();

        if (userRepository.existsByEmail(user.getEmail())) {
            throw LoginErrorCode.USERNAME_ALREADY_EXISTS.exception();
        }
        String accessToken = jwtUtil.createAccessToken(user);
        String refreshToken = jwtUtil.createRefreshToken(user);
        userRepository.save(user);
        refreshTokenRepository.save(
                RefreshToken.builder()
                        .user(user)
                        .token(refreshToken)
                        .build()
        );
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
