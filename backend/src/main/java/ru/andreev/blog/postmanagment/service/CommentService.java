package ru.andreev.blog.postmanagment.service;

import org.springframework.http.ResponseEntity;
import ru.andreev.blog.domain.dto.request.CommentRequest;

public interface CommentService {

    ResponseEntity<?> saveComment(Long postId, CommentRequest commentRequest, String username);

    ResponseEntity<?> deleteComment(Long postId, Long commentId, String username);
}
