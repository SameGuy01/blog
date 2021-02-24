package ru.andreev.blog.postmanagment.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import ru.andreev.blog.postmanagment.exception.UserNotFoundException;
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

    private final static String CREATE_SUCCESSFUL = "Post was created successfully.";
    private final static String UPDATE_SUCCESSFUL = "Post was updated successfully.";
    private final static String DELETE_SUCCESSFUL = "Post was deleted successfully";

    private final static String INVALID_USERNAME = "Invalid username id.";
    private final static String INVALID_POST_USER = "Post's user is incorrect.";

    public PostServiceImpl(PostMapper postMapper, UserRepository userRepository, PostRepository postRepository) {
        this.postMapper = postMapper;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public ResponseEntity<?> findById(Long postId, Long userId) {

        if(userRepository.getById(userId).isEmpty()){
           return ResponseEntity.badRequest().body(INVALID_USERNAME);
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        if(!post.getUser().getId().equals(userId)){
            return ResponseEntity.badRequest().body(INVALID_USERNAME);
        }

        return ResponseEntity.ok().body(postMapper.toDto(post));
    }

    @Override
    public ResponseEntity<?> savePost(Long userId, PostRequest postRequest, String username) {

        User user = getUserById(userId);

        if(!user.getUsername().equals(username)){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(INVALID_USERNAME));
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
                    .body(new MessageResponse(INVALID_USERNAME));
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

        return ResponseEntity.ok().body(new MessageResponse(DELETE_SUCCESSFUL));
    }

    @Override
    public ResponseEntity<?> findAllByUserId(Long userId) {

        User user = getUserById(userId);

        List<PostResponse> postList = postRepository.getAllByUser(user)
                .stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(postList);
    }

    @Override
    public ResponseEntity<?> findAllBySubscriptions(Long userId) {

        if(userRepository.getById(userId).isEmpty()){
            throw new UserNotFoundException();
        }

        List<PostResponse> postList = postRepository.getAllBySubscription(userId)
                .stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(postList);
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
