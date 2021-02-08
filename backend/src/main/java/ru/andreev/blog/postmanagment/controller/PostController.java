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
@RequestMapping(produces = "application/json", path = "/api/v/0/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findPostById(@PathVariable Long id){
        return postService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createNewPost(@Valid @RequestBody final PostRequest postRequest,
                                           @AuthenticationPrincipal final UserDetails userDetails){
        return postService.savePost(postRequest,userDetails.getUsername());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updatePost(@PathVariable Long id,
                                        @Valid @RequestBody final PostEditRequest postEditRequest,
                                        @AuthenticationPrincipal final UserDetails userDetails){
        return postService.updatePost(id,postEditRequest, userDetails.getUsername());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deletePost(@PathVariable Long id,
                                        @AuthenticationPrincipal final UserDetails userDetails){
        return postService.deleteById(id, userDetails.getUsername());
    }
}
