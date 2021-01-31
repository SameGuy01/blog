package ru.andreev.blog.usermanagment.service;

import org.springframework.http.ResponseEntity;
import ru.andreev.blog.domain.dto.request.LogInRequest;
import ru.andreev.blog.domain.dto.request.SignUpRequest;
import ru.andreev.blog.domain.dto.response.JwtResponse;

public interface UserService {

    ResponseEntity<?> authenticateUser(LogInRequest logInRequest);

    ResponseEntity<?> registerUser(SignUpRequest signUpRequest);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
