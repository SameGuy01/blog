package ru.andreev.blog.postmanagment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.andreev.blog.domain.dto.request.PostEditRequest;
import ru.andreev.blog.domain.dto.request.PostRequest;
import ru.andreev.blog.postmanagment.service.PostService;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(produces = "application/json", path = "/api/v/0/users/{userId}/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllByUser(@PathVariable final Long userId){
        return postService.getAllByUserId(userId);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> findPostById(@PathVariable final Long postId,
                                          @PathVariable final Long userId) {
        return postService.findById(postId, userId);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createNewPost(@PathVariable final Long userId,
                                           @Valid @RequestBody final PostRequest postRequest,
                                           @AuthenticationPrincipal final UserDetails userDetails){
        return postService.savePost(userId, postRequest, userDetails.getUsername());
    }

    @PutMapping("/{postId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updatePost(@PathVariable final Long postId,
                                        @PathVariable final Long userId,
                                        @Valid @RequestBody final PostEditRequest postEditRequest,
                                        @AuthenticationPrincipal final UserDetails userDetails){
        return postService.updatePost(postId, userId, postEditRequest, userDetails.getUsername());
    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deletePost(@PathVariable final Long postId,
                                        @PathVariable final Long userId,
                                        @AuthenticationPrincipal final UserDetails userDetails){
        return postService.deleteById(postId, userId, userDetails.getUsername());
    }
}
