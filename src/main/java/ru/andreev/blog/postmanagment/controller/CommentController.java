package ru.andreev.blog.postmanagment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.andreev.blog.domain.dto.request.CommentRequest;
import ru.andreev.blog.postmanagment.service.CommentService;

import javax.validation.Valid;

@RestController
@RequestMapping(produces = "application/json", path = "/api/v/0/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveComment(@Valid @RequestBody CommentRequest commentRequest,
                                         @AuthenticationPrincipal UserDetails userDetails){
        return commentService.saveComment(commentRequest, userDetails.getUsername());
    }
}
