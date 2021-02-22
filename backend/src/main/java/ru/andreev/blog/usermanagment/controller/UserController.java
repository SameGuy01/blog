package ru.andreev.blog.usermanagment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.andreev.blog.domain.dto.request.UserInfoEditRequest;
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

    @GetMapping("/{id}")
    public ResponseEntity<?> findUserById(@PathVariable Long id){
        return userService.findById(id);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateUser(@PathVariable Long id,
                                        @Valid @RequestBody UserInfoEditRequest userInfoEditRequest,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        return userService.updateUser(id, userDetails.getUsername(), userInfoEditRequest);
    }


    @PatchMapping("/{userId}/subscribe/{channelId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> subscribe(@PathVariable Long userId,
                                       @PathVariable Long channelId,
                                       @AuthenticationPrincipal UserDetails userDetails){
        return userService.subscribe(userId, channelId, userDetails.getUsername());
    }

    @PatchMapping("/{userId}/unsubscribe/{channelId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> unsubscribe(@PathVariable Long userId,
                                         @PathVariable Long channelId,
                                         @AuthenticationPrincipal UserDetails userDetails){
        return userService.unsubscribe(userId,channelId,userDetails.getUsername());
    }
}
