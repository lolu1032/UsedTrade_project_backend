package com.example.demo.blog.Auth.service;

import com.example.demo.blog.Auth.domain.RefreshToken;
import com.example.demo.blog.Auth.domain.Users;
import com.example.demo.blog.Auth.domain.VerificationToken;
import com.example.demo.blog.Auth.dto.LoginCommandDtos.*;
import com.example.demo.blog.Auth.dto.PasswordResetDtos.*;
import com.example.demo.blog.Auth.exception.LoginErrorCode;
import com.example.demo.blog.Auth.repository.RefreshTokenRepository;
import com.example.demo.blog.Auth.repository.UserRepository;
import com.example.demo.blog.Auth.repository.VerificationTokenRepository;
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
    private final VerificationTokenRepository verificationTokenRepository;
    private final EmailService emailService;
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
    // Add these methods to your UserService class

    public PasswordResetResponse requestPasswordReset(PasswordResetRequest request) {
        Users user = userRepository.findByEmail(request.email())
                .orElseThrow(LoginErrorCode.EMAIL_NOT_FOUND::exception);

        // Check if a token already exists and delete it
        VerificationToken existingToken = verificationTokenRepository.findByUserId(user.getId()).orElse(null);
        if (existingToken != null) {
            verificationTokenRepository.delete(existingToken);
        }

        // Create new token
        VerificationToken verificationToken = VerificationToken.createToken(user);
        verificationTokenRepository.save(verificationToken);

        // Create verification link
        String resetLink = "http://your-frontend-url/reset-password?token=" + verificationToken.getToken();

        // Send email
        emailService.sendVerificationEmail(
                user.getEmail(),
                "비밀번호 재설정",
                resetLink
        );

        return PasswordResetResponse.builder()
                .message("비밀번호 재설정 이메일이 발송되었습니다.")
                .build();
    }

    public PasswordResetResponse resetPassword(PasswordUpdateRequest request) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(request.token())
                .orElseThrow(() -> new RuntimeException("유효하지 않은 토큰입니다."));

        if (verificationToken.isExpired()) {
            verificationTokenRepository.delete(verificationToken);
            throw new RuntimeException("만료된 토큰입니다. 다시 요청해주세요.");
        }

        Users user = verificationToken.getUser();
        user.updatePassword(bCryptPasswordEncoder.encode(request.newPassword()));
        userRepository.save(user);

        // Delete the used token
        verificationTokenRepository.delete(verificationToken);

        return PasswordResetResponse.builder()
                .message("비밀번호가 성공적으로 변경되었습니다.")
                .build();
    }
}
