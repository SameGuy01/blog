package ru.asteac.blog.postmanagment.service;

import org.springframework.http.ResponseEntity;
<<<<<<< HEAD:backend/src/main/java/ru/andreev/blog/postmanagment/service/PostService.java
import org.springframework.security.core.userdetails.UserDetails;
import ru.andreev.blog.domain.dto.request.PostEditRequest;
import ru.andreev.blog.domain.dto.request.PostRequest;
=======
import ru.asteac.blog.domain.dto.request.PostEditRequest;
import ru.asteac.blog.domain.dto.request.PostRequest;
>>>>>>> upstream/main:backend/src/main/java/ru/asteac/blog/postmanagment/service/PostService.java

public interface PostService {

    ResponseEntity<?> findById(Long postId, Long userId);

    ResponseEntity<?> savePost(Long userId, PostRequest postRequest, String username);

    ResponseEntity<?> updatePost(Long postId, Long userId,  PostEditRequest postEditRequest, String username);

    ResponseEntity<?> deleteById(Long postId, Long userId, String username);

    ResponseEntity<?> findAllByUserId(Long userId);

    ResponseEntity<?> findAllBySubscription(Long userId, String username);

    ResponseEntity<?> like(Long postId,Long userId, String username);

    ResponseEntity<?> removeLike(Long postId,Long userId, String username);
}
