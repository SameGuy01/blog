package ru.asteac.blog.postmanagment.service;

import org.springframework.http.ResponseEntity;
import ru.asteac.blog.domain.dto.request.PostEditRequest;
import ru.asteac.blog.domain.dto.request.PostRequest;

public interface PostService {

    ResponseEntity<?> findById(Long postId, Long userId);

    ResponseEntity<?> savePost(Long userId, PostRequest postRequest, String username);

    ResponseEntity<?> updatePost(Long postId, Long userId,  PostEditRequest postEditRequest, String username);

    ResponseEntity<?> deleteById(Long postId, Long userId, String username);

    ResponseEntity<?> findAllByUserId(Long userId);

    ResponseEntity<?> findAllBySubscription(Long userId, String username);
}
