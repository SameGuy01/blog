package ru.asteac.blog.postmanagment.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.asteac.blog.domain.dto.request.PostEditRequest;
import ru.asteac.blog.domain.dto.request.PostRequest;
import ru.asteac.blog.domain.dto.response.MessageResponse;
import ru.asteac.blog.domain.dto.response.SimplePostResponse;
import ru.asteac.blog.domain.mapper.PostMapper;
import ru.asteac.blog.domain.model.entity.Post;
import ru.asteac.blog.domain.model.entity.User;
import ru.asteac.blog.postmanagment.exception.PostNotFoundException;
import ru.asteac.blog.postmanagment.exception.UserNotFoundException;
import ru.asteac.blog.postmanagment.repository.PostRepository;
import ru.asteac.blog.postmanagment.service.PostService;
import ru.asteac.blog.usermanagment.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private final static String CREATE_SUCCESSFUL = "Post was created successfully.";
    private final static String UPDATE_SUCCESSFUL = "Post was updated successfully.";
    private final static String DELETE_SUCCESSFUL = "Post was deleted successfully";
    private final static String LIKE_SUCCESSFUL = "Like was added successfully";
    private final static String REMOVE_LIKE_SUCCESSFUL = "Like was removed successfully";


    private final static String INVALID_USER = "Invalid user id.";
    private final static String INVALID_POST_USER = "Post's user is incorrect.";

    public PostServiceImpl(PostMapper postMapper, UserRepository userRepository, PostRepository postRepository) {
        this.postMapper = postMapper;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public ResponseEntity<?> findById(Long postId, Long userId) {

        if(userRepository.getById(userId).isEmpty()){
           return ResponseEntity.badRequest().body(INVALID_USER);
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        if(!post.getUser().getId().equals(userId)){
            return ResponseEntity.badRequest().body(INVALID_USER);
        }

        return ResponseEntity.ok().body(postMapper.toDto(post));
    }

    @Override
    public ResponseEntity<?> savePost(Long userId, PostRequest postRequest, String username) {

        User user = getUserById(userId);

        if(!user.getUsername().equals(username)){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(INVALID_USER));
        }

        Post post = postMapper.toEntity(postRequest);
        post.setUser(user);
        post.setCreatedAt(LocalDateTime.now());

        postRepository.save(post);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MessageResponse(CREATE_SUCCESSFUL));
    }

    @Override
    public ResponseEntity<?> updatePost(Long postId, Long userId, PostEditRequest postEditRequest, String username) {

        User user = getUserById(userId);

        if(!user.getUsername().equals(username)){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(INVALID_USER));
        }

        Post fromDbPost = getPostById(postId);

        if(!user.equals(fromDbPost.getUser())){
            return ResponseEntity.badRequest().body(INVALID_POST_USER);
        }

        Post updatedPost = postMapper.toEntity(postEditRequest);

        BeanUtils.copyProperties(updatedPost,fromDbPost,"id", "user", "commentList", "createdAt");
        fromDbPost.setUpdatedAt(LocalDateTime.now());
        postRepository.save(fromDbPost);

        return ResponseEntity.ok().body(new MessageResponse(UPDATE_SUCCESSFUL));
    }

    @Override
    public ResponseEntity<?> deleteById(Long postId, Long userId, String username) {
        Post post = getPostById(postId);
        if(!post.getUser().getUsername().equals(username)){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(INVALID_POST_USER));
        }

        postRepository.delete(post);

        return ResponseEntity.ok().body(new MessageResponse(DELETE_SUCCESSFUL));
    }

    @Override
    public ResponseEntity<?> findAllByUserId(Long userId) {

        User user = getUserById(userId);

        List<SimplePostResponse> postList = postRepository.getAllByUser(user)
                .stream()
                .map(postMapper::toSimpleDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(postList);
    }

    @Override
    public ResponseEntity<?> findAllBySubscription(Long userId, String username) {

        User user = getUserById(userId);

        if(!user.getUsername().equals(username)){
            return ResponseEntity.badRequest().body(INVALID_POST_USER);
        }

        List<SimplePostResponse> postList = postRepository.getAllBySubscription(userId)
                .stream()
                .map(postMapper::toSimpleDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(postList);
    }

    @Override
    public ResponseEntity<?> removeLike(Long postId, Long userId, String username){

        User author = getUserById(userId);
        Post post = getPostById(postId);

        if(!post.getUser().equals(author)){
            return ResponseEntity.badRequest().body(INVALID_USER);
        }

        User user = userRepository.getByUsername(username).orElseThrow(UserNotFoundException::new);

        post.removeLike(user);
        postRepository.save(post);

        return ResponseEntity.ok().body(new MessageResponse(REMOVE_LIKE_SUCCESSFUL));
    }


    @Override
    public ResponseEntity<?> like(Long postId, Long userId, String username) {
        Post post = getPostById(postId);

        User author = getUserById(userId);

        if(!post.getUser().equals(author)){
            return ResponseEntity.badRequest().body(INVALID_POST_USER);
        }

        User user = userRepository.getByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        post.like(user);
        postRepository.save(post);

        return ResponseEntity.ok().body(LIKE_SUCCESSFUL);
    }

    private Post getPostById(Long id){
        return postRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    private User getUserById(Long id){
        return userRepository.getById(id)
                .orElseThrow(UserNotFoundException::new);
    }
}
