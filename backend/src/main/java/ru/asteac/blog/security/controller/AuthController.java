package ru.asteac.blog.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.asteac.blog.domain.dto.request.LogInRequest;
import ru.asteac.blog.domain.dto.request.SignUpRequest;
import ru.asteac.blog.usermanagment.service.UserService;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(produces = "application/json", path = "/api/v/0/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser (@Valid @RequestBody LogInRequest logInRequest){
        return userService.authenticateUser(logInRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody
                                                      SignUpRequest signUpRequest){
       return userService.registerUser(signUpRequest);
    }
}
