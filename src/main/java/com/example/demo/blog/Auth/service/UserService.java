package com.example.demo.blog.Auth.service;

import com.example.demo.blog.Auth.domain.RefreshToken;
import com.example.demo.blog.Auth.domain.Users;
import com.example.demo.blog.Auth.dto.LoginCommandDtos.*;
import com.example.demo.blog.Auth.exception.LoginErrorCode;
import com.example.demo.blog.Auth.repository.RefreshTokenRepository;
import com.example.demo.blog.Auth.repository.UserRepository;
import com.example.demo.blog.Auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    /**
     * 이메일이 없을 시 EMAIL_NOT_FOUND 예외 처리
     * 또는 이메일은 존재하지만 비밀번호가 틀릴 시 PASSWORD_NOT_FOUND 예외 처리
     * @param loginSelectResponse
     * @return
     */
    public accessTokenResponse  login(LoginSelectResponse loginSelectResponse) {
        Users user = userRepository.findByEmail(loginSelectResponse.email())
            .orElseThrow(
                    LoginErrorCode.EMAIL_NOT_FOUND::exception
            );
        if(!bCryptPasswordEncoder.matches(loginSelectResponse.password(), user.getPassword())) {
            throw LoginErrorCode.PASSWORD_NOT_FOUND.exception();
        }
        RefreshToken refreshTokenEntity  = refreshTokenRepository.findByUserId(user.getId())
                .orElseThrow(LoginErrorCode.TOKEN_NOT_FOUND::exception);
        String accessToken = "";
        String refreshToken = refreshTokenEntity.getToken();
        if (jwtUtil.isValidRefreshToken(refreshToken)) {
            accessToken = jwtUtil.createAccessToken(user);
        } else {
            refreshToken = jwtUtil.createRefreshToken(user);
            refreshTokenEntity.updateToken(refreshToken);
            refreshTokenRepository.save(refreshTokenEntity);
        }
        return accessTokenResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    /**
     * Builder한 정보가 만약 있으면 USERNAME_ALREADY_EXISTS로 예외처리 없으면 저장
     * @param loginSelectRequest
     * @return
     */
    public TokensResponse sign(LoginSelectRequest loginSelectRequest) {
        Users user = Users.builder()
                .email(loginSelectRequest.email())
                .password(bCryptPasswordEncoder.encode(loginSelectRequest.password()))
                .username(loginSelectRequest.username())
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
        return TokensResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
