package ru.andreev.blog.postmanagment.service;

import org.springframework.http.ResponseEntity;
import ru.andreev.blog.domain.dto.request.PostEditRequest;
import ru.andreev.blog.domain.dto.request.PostRequest;

public interface PostService {

    ResponseEntity<?> findById(Long id);

    ResponseEntity<?> savePost(PostRequest postRequest, String username);

    ResponseEntity<?> updatePost(Long id, PostEditRequest postEditRequest, String username);

    ResponseEntity<?> deleteById(Long id, String username);
}
