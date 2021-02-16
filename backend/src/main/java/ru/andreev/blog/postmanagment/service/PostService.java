package ru.andreev.blog.postmanagment.service;

import org.springframework.http.ResponseEntity;
import ru.andreev.blog.domain.dto.request.PostEditRequest;
import ru.andreev.blog.domain.dto.request.PostRequest;

public interface PostService {

    ResponseEntity<?> findById(Long id, Long userId);

    ResponseEntity<?> savePost( Long userId, PostRequest postRequest, String username);

    ResponseEntity<?> updatePost(Long postId, Long userId,  PostEditRequest postEditRequest, String username);

    ResponseEntity<?> deleteById(Long postId, Long userId, String username);

    ResponseEntity<?> getAllByUserId(Long userId);
}
