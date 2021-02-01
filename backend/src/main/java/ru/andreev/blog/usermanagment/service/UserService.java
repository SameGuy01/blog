package ru.andreev.blog.usermanagment.service;

import org.springframework.http.ResponseEntity;
import ru.andreev.blog.domain.dto.request.LogInRequest;
import ru.andreev.blog.domain.dto.request.SignUpRequest;

public interface UserService {

    ResponseEntity<?> authenticateUser(LogInRequest logInRequest);

    ResponseEntity<?> registerUser(SignUpRequest signUpRequest);
}
