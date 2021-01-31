package ru.andreev.blog.postmanagment.service;

import org.springframework.http.ResponseEntity;
import ru.andreev.blog.domain.dto.request.PostRequest;
import ru.andreev.blog.domain.model.entity.User;

public interface PostService {

    ResponseEntity<?> findById(Long id);

    ResponseEntity<?> savePost(PostRequest postRequest, String username);
}
