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
import ru.andreev.blog.domain.dto.response.PostResponse;
import ru.andreev.blog.domain.mapper.PostMapper;
import ru.andreev.blog.domain.model.entity.Post;
import ru.andreev.blog.domain.model.entity.User;
import ru.andreev.blog.postmanagment.exception.PostNotFoundException;
import ru.andreev.blog.postmanagment.repository.PostRepository;
import ru.andreev.blog.postmanagment.service.PostService;
import ru.andreev.blog.usermanagment.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private final static String CREATE_SUCCESS = "The post was created successfully.";
    private final static String UPDATE_SUCCESS = "The post was updated successfully.";
    private final static String DELETE_SUCCESS = "The post was deleted successfully";

    private final static String CREATE_ERROR = "The user can only create their own post.";
    private final static String UPDATE_ERROR = "The user can only update their own post.";
    private final static String DELETE_ERROR = "The user can only delete their own post.";

    public PostServiceImpl(PostMapper postMapper, UserRepository userRepository, PostRepository postRepository) {
        this.postMapper = postMapper;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public ResponseEntity<?> findById(Long id, Long userId) {
        getUserById(id);
        return ResponseEntity.ok().body(postMapper.toDto(getPostById(id)));
    }

    @Override
    public ResponseEntity<?> savePost(Long userId, PostRequest postRequest, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        if(!user.getId().equals(userId)){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new MessageResponse(CREATE_ERROR));
        }

        Post post = postMapper.toEntity(postRequest);

        post.setUser(user);
        post.setCreatedAt(LocalDateTime.now());

        postRepository.save(post);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MessageResponse(CREATE_SUCCESS));
    }

    @Override
    public ResponseEntity<?> updatePost(Long postId, Long userId, PostEditRequest postEditRequest, String username) {

        User user = getUserById(userId);
        Post fromDbPost = getPostById(postId);

        if(!user.getUsername().equals(username) || !user.getId().equals(userId)){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new MessageResponse(UPDATE_ERROR));
        }

        Post updatedPost = postMapper.toEntity(postEditRequest);

        BeanUtils.copyProperties(updatedPost,fromDbPost,"id", "user", "commentList", "createdAt");
        fromDbPost.setUpdatedAt(LocalDateTime.now());
        postRepository.save(fromDbPost);

        return ResponseEntity.ok().body(new MessageResponse(UPDATE_SUCCESS));
    }

    @Override
    public ResponseEntity<?> deleteById(Long postId, Long userId, String username) {
        Post post = getPostById(postId);
        if(!post.getUser().getUsername().equals(username)){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new MessageResponse(DELETE_ERROR));
        }

        return ResponseEntity.ok().body(new MessageResponse(DELETE_SUCCESS));
    }

    @Override
    public ResponseEntity<?> getAllByUserId(Long userId) {

        User user = userRepository.getById(userId)
                .orElseThrow(() -> new UsernameNotFoundException(String.valueOf(userId)));

        List<PostResponse> postList = postRepository.getAllByUser(user)
                .stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(postList);
    }

    private Post getPostById(Long id){
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(String.valueOf(id)));
    }

    private User getUserById(Long id){
        return userRepository.getById(id)
                .orElseThrow(() -> new UsernameNotFoundException(String.valueOf(id)));
    }
}
