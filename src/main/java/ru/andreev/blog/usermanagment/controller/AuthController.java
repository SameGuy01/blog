package ru.andreev.blog.usermanagment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.andreev.blog.usermanagment.payload.request.SignUpRequest;
import ru.andreev.blog.usermanagment.payload.responce.MessageResponse;
import ru.andreev.blog.usermanagment.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v/0/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public ResponseEntity<?> test(){
        return ResponseEntity.ok(new MessageResponse("ok"));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody
                                                      SignUpRequest signUpRequest){
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        userService.registerUser(signUpRequest);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
