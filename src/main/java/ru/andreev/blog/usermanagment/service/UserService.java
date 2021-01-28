package ru.andreev.blog.usermanagment.service;

import ru.andreev.blog.domain.dto.request.LogInRequest;
import ru.andreev.blog.domain.dto.request.SignUpRequest;
import ru.andreev.blog.domain.dto.response.JwtResponse;

public interface UserService {

    JwtResponse authenticateUser(LogInRequest logInRequest);

    void registerUser(SignUpRequest signUpRequest);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
