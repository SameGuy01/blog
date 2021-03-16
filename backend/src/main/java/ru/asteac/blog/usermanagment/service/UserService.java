package ru.asteac.blog.usermanagment.service;

import org.springframework.http.ResponseEntity;
import ru.asteac.blog.domain.dto.request.LogInRequest;
import ru.asteac.blog.domain.dto.request.SignUpRequest;
import ru.asteac.blog.domain.dto.request.UserInfoEditRequest;

public interface UserService {

    ResponseEntity<?> authenticateUser(LogInRequest logInRequest);

    ResponseEntity<?> registerUser(SignUpRequest signUpRequest);

    ResponseEntity<?> updateUser(Long userId, String username, UserInfoEditRequest userInfoEditRequest);

    ResponseEntity<?> getById(Long id);

    ResponseEntity<?> subscribe(Long userId, Long channelId, String username);

    ResponseEntity<?> unsubscribe(Long userId, Long channelId, String username);
}
