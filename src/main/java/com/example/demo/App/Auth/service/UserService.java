package com.example.demo.App.Auth.service;

import com.example.demo.App.Auth.domain.RefreshToken;
import com.example.demo.App.Auth.domain.Users;
import com.example.demo.App.Auth.domain.VerificationToken;
import com.example.demo.App.Auth.dto.LoginCommandDtos.*;
import com.example.demo.App.Auth.dto.PasswordResetDtos.*;
import com.example.demo.App.Auth.exception.LoginErrorCode;
import com.example.demo.App.Auth.exception.PwResetErrorCode;
import com.example.demo.App.Auth.repository.RefreshTokenRepository;
import com.example.demo.App.Auth.repository.UserRepository;
import com.example.demo.App.Auth.repository.VerificationTokenRepository;
import com.example.demo.App.Auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

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
        if(user.matchPassword(bCryptPasswordEncoder,loginSelectResponse.password())) {
            throw LoginErrorCode.PASSWORD_NOT_FOUND.exception();
        }
        RefreshToken refreshTokenEntity  = refreshTokenRepository.findByUserId(user.fetchUserId())
                .orElseThrow(LoginErrorCode.TOKEN_NOT_FOUND::exception);
        String accessToken = "";
        String refreshToken = refreshTokenEntity.fetchToken();
        if (jwtUtil.isValidRefreshToken(refreshToken)) {
            accessToken = jwtUtil.createAccessToken(user);
        } else {
            refreshToken = jwtUtil.createRefreshToken(user);
            refreshTokenEntity.updateToken(refreshToken);
            refreshTokenRepository.save(refreshTokenEntity);
        }
        return accessTokenResponse.builder()
                .username(user.getUsername())
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

        if (userRepository.existsByEmail(user.fetchUserEmail())) {
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

    @Scheduled(fixedRate = 3600000)
    public void deleteExpiredTokens() {
        verificationTokenRepository.deleteByExpiryDateBefore(Instant.now());
    }

    public PasswordResetResponse requestPasswordReset(PasswordResetRequest request) {
        Users user = userRepository.findByEmail(request.email())
                .orElseThrow(LoginErrorCode.EMAIL_NOT_FOUND::exception);


        VerificationToken existingToken = verificationTokenRepository.findByUserId(user.fetchUserId()).orElse(null);
        if (existingToken != null) {
            verificationTokenRepository.delete(existingToken);
        }


        VerificationToken verificationToken = VerificationToken.createToken(user);
        verificationTokenRepository.save(verificationToken);


        String resetLink = "http://localhost:9000/reset-password?token=" + verificationToken.getToken();


        emailService.sendVerificationEmail(
                user.fetchUserEmail(),
                "비밀번호 재설정",
                resetLink
        );

        return PasswordResetResponse.builder()
                .message("비밀번호 재설정 이메일이 발송되었습니다.")
                .build();
    }

    public PasswordResetResponse resetPassword(PasswordUpdateRequest request) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(request.token())
                .orElseThrow(PwResetErrorCode.TOKEN_NOT_FOUNT::exception);

        if (verificationToken.isExpired()) {
            verificationTokenRepository.delete(verificationToken);
            throw PwResetErrorCode.EXPIRED_TOKEN.exception();
        }

        Users user = verificationToken.getUser();
        user.updatePassword(bCryptPasswordEncoder.encode(request.newPassword()));
        userRepository.save(user);


        verificationTokenRepository.delete(verificationToken);

        return PasswordResetResponse.builder()
                .message("비밀번호가 성공적으로 변경되었습니다.")
                .build();
    }
}
