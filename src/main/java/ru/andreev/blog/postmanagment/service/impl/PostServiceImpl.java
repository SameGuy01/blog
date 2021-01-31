package ru.andreev.blog.postmanagment.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andreev.blog.domain.dto.request.PostRequest;
import ru.andreev.blog.domain.mapper.PostMapper;
import ru.andreev.blog.domain.model.entity.Category;
import ru.andreev.blog.domain.model.entity.Post;
import ru.andreev.blog.domain.model.entity.User;
import ru.andreev.blog.postmanagment.exception.CategoryNotFountException;
import ru.andreev.blog.postmanagment.exception.PostNotFoundException;
import ru.andreev.blog.postmanagment.repository.CategoryRepository;
import ru.andreev.blog.postmanagment.repository.PostRepository;
import ru.andreev.blog.postmanagment.service.PostService;
import ru.andreev.blog.usermanagment.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    public PostServiceImpl(PostMapper postMapper, UserRepository userRepository, PostRepository postRepository, CategoryRepository categoryRepository) {
        this.postMapper = postMapper;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ResponseEntity<?> findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        return ResponseEntity.ok(post);
    }

    @Override
    public ResponseEntity<?> savePost(PostRequest postRequest, String username) {

        Category category = categoryRepository.findById(Long.valueOf(postRequest.getCategoryId()))
                .orElseThrow(() -> new CategoryNotFountException(Long.valueOf(postRequest.getCategoryId())));
        Post post = postMapper.toEntity(postRequest);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User is not found"));

        post.setUser(user);
        post.setCategory(category);
        post.setCreatedAt(LocalDateTime.now());

        postRepository.save(post);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
