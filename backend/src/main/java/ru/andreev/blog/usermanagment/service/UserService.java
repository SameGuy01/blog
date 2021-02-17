package ru.andreev.blog.usermanagment.service;

import org.springframework.http.ResponseEntity;
import ru.andreev.blog.domain.dto.request.LogInRequest;
import ru.andreev.blog.domain.dto.request.SignUpRequest;
import ru.andreev.blog.domain.dto.request.UserEditRequest;

public interface UserService {

    ResponseEntity<?> authenticateUser(LogInRequest logInRequest);

    ResponseEntity<?> registerUser(SignUpRequest signUpRequest);

    ResponseEntity<?> updateUser(Long userId, String username, UserEditRequest userEditRequest);
}
