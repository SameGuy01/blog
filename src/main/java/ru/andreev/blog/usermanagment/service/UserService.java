package ru.andreev.blog.usermanagment.service;

import ru.andreev.blog.model.entity.User;
import ru.andreev.blog.usermanagment.payload.request.SignUpRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void registerUser(SignUpRequest signUpRequest);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
