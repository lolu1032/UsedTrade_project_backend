package com.example.demo.blog.service;

import com.example.demo.blog.domain.Users;
import com.example.demo.blog.dto.LoginCommandDtos.*;
import com.example.demo.blog.exception.LoginErrorCode;
import com.example.demo.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /**
     * 이메일이 없을 시 EMAIL_NOT_FOUND 예외 처리
     * 또는 이메일은 존재하지만 비밀번호가 틀릴 시 PASSWORD_NOT_FOUND 예외 처리
     * @param users
     * @return
     */
    public LoginSelectResponse login(Users users) {
        Users user = userRepository.findByEmail(users.getEmail())
            .orElseThrow(
                    LoginErrorCode.EMAIL_NOT_FOUND::exception
            );
        if(!userRepository.existsByPassword(user.getPassword())) {
            throw LoginErrorCode.PASSWORD_NOT_FOUND.exception();
        }
        return LoginSelectResponse.builder()
                .message("로그인성공")
                .users(user)
                .build();
    }

    /**
     * Builder한 정보가 만약 있으면 USERNAME_ALREADY_EXISTS로 예외처리 없으면 저장
     * @param loginSelectRequest
     * @return
     */
    public Users sign(LoginSelectRequest loginSelectRequest) {
        Users user = Users.builder()
                .email(loginSelectRequest.email())
                .password(loginSelectRequest.password())
                .build();

        if (userRepository.existsByEmail(user.getEmail())) {
            throw LoginErrorCode.USERNAME_ALREADY_EXISTS.exception();
        }
        return userRepository.save(user);
    }

}
