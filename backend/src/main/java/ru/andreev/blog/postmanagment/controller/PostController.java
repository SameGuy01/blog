package ru.andreev.blog.postmanagment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.andreev.blog.domain.dto.request.PostRequest;
import ru.andreev.blog.postmanagment.service.PostService;

import javax.validation.Valid;

@RestController
@RequestMapping(produces = "application/json", path = "/api/v/0/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createNewPost(@Valid @RequestBody final PostRequest postRequest,
                                           @AuthenticationPrincipal UserDetails userDetails){
        return postService.savePost(postRequest,userDetails.getUsername());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return postService.findById(id);
    }
}
