package ru.andreev.blog.usermanagment.service;

import ru.andreev.blog.usermanagment.payload.request.LogInRequest;
import ru.andreev.blog.usermanagment.payload.request.SignUpRequest;
import ru.andreev.blog.usermanagment.payload.responce.JwtResponse;

public interface UserService {

    JwtResponse authenticateUser(LogInRequest logInRequest);

    void registerUser(SignUpRequest signUpRequest);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
