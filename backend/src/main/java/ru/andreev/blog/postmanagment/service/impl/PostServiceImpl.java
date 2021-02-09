package ru.andreev.blog.postmanagment.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andreev.blog.domain.dto.request.PostEditRequest;
import ru.andreev.blog.domain.dto.request.PostRequest;
import ru.andreev.blog.domain.dto.response.MessageResponse;
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
                .orElseThrow(() -> new PostNotFoundException(String.valueOf(id)));

        return ResponseEntity.ok().body(postMapper.toDto(post));
    }

    @Override
    public ResponseEntity<?> savePost(PostRequest postRequest, String username) {

        Category category = categoryRepository.findById(Long.valueOf(postRequest.getCategoryId()))
                .orElseThrow(() -> new CategoryNotFountException(postRequest.getCategoryId()));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        Post post = postMapper.toEntity(postRequest);

        post.setUser(user);
        post.setCategory(category);
        post.setCreatedAt(LocalDateTime.now());

        postRepository.save(post);
        return ResponseEntity.ok().body(postMapper.toDto(post));
    }

    @Override
    public ResponseEntity<?> updatePost(Long id, PostEditRequest postEditRequest, String username) {

        Post fromDbPost = getPostById(id);

        if(!fromDbPost.getUser().getUsername().equals(username)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("User can only update their own posts."));
        }

        Post updatedPost = postMapper.toEntity(postEditRequest);

        BeanUtils.copyProperties(updatedPost,fromDbPost,"id", "user", "commentList", "createdAt");
        fromDbPost.setUpdatedAt(LocalDateTime.now());
        postRepository.save(fromDbPost);

        return ResponseEntity.ok().body(postMapper.toDto(fromDbPost));
    }

    @Override
    public ResponseEntity<?> deleteById(Long id, String username) {
        Post post = getPostById(id);
        if(!post.getUser().getUsername().equals(username)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Users can only delete their own posts."));
        }

        return ResponseEntity.ok().body(new MessageResponse("Post " + id + " is deleted successful."));
    }

    private Post getPostById(Long id){
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(String.valueOf(id)));
    }
}
