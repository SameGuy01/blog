package ru.andreev.blog.postmanagment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.andreev.blog.domain.dto.request.CommentRequest;
import ru.andreev.blog.postmanagment.service.CommentService;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(produces = "application/json", path = "/api/v/0/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveComment(@PathVariable final Long postId,
                                         @Valid @RequestBody final CommentRequest commentRequest,
                                         @AuthenticationPrincipal final UserDetails userDetails){
        return commentService.saveComment(postId,commentRequest, userDetails.getUsername());
    }

    @DeleteMapping("/{commentId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteComment(@PathVariable final Long postId,
                                           @PathVariable final Long commentId,
                                           @AuthenticationPrincipal final  UserDetails userDetails){
        return commentService.deleteComment(postId, commentId, userDetails.getUsername());
    }
}
