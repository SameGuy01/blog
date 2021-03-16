package ru.asteac.blog.postmanagment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.asteac.blog.domain.dto.request.CommentRequest;
import ru.asteac.blog.postmanagment.service.CommentService;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(produces = "application/json", path = "/api/v/0/users/{channelId}/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<?> findAllComments(@PathVariable Long channelId,
                                             @PathVariable Long postId){
        return commentService.getAllByPostId(channelId, postId);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveComment(@PathVariable final Long channelId,
                                         @PathVariable final Long postId,
                                         @Valid @RequestBody final CommentRequest commentRequest,
                                         @AuthenticationPrincipal final UserDetails userDetails){
        return commentService.saveComment(channelId,postId,commentRequest, userDetails.getUsername());
    }

    @DeleteMapping("/{commentId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteComment(@PathVariable final Long channelId,
                                           @PathVariable final Long postId,
                                           @PathVariable final Long commentId,
                                           @AuthenticationPrincipal final  UserDetails userDetails){
        return commentService.deleteComment(channelId,postId, commentId, userDetails.getUsername());
    }
}
