package ru.andreev.blog.usermanagment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.andreev.blog.domain.dto.request.UserEditRequest;
import ru.andreev.blog.usermanagment.service.UserService;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(produces = "application/json", path ="/api/v/0/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateUser(@PathVariable Long id,
                                        @Valid @RequestBody UserEditRequest userEditRequest,
                                        @AuthenticationPrincipal UserDetails userDetails){
        return userService.updateUser(id, userDetails.getUsername(), userEditRequest);
    }
}
