package ru.asteac.blog.postmanagment.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.asteac.blog.domain.dto.request.CommentRequest;
import ru.asteac.blog.domain.dto.response.CommentResponse;
import ru.asteac.blog.domain.dto.response.MessageResponse;
import ru.asteac.blog.domain.mapper.CommentMapper;
import ru.asteac.blog.domain.model.entity.Comment;
import ru.asteac.blog.domain.model.entity.Post;
import ru.asteac.blog.domain.model.entity.User;
import ru.asteac.blog.exception.CommentNotFoundException;
import ru.asteac.blog.exception.PostNotFoundException;
import ru.asteac.blog.exception.UserNotFoundException;
import ru.asteac.blog.postmanagment.repository.CommentRepository;
import ru.asteac.blog.postmanagment.repository.PostRepository;
import ru.asteac.blog.postmanagment.service.CommentService;
import ru.asteac.blog.usermanagment.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private final static String CREATE_SUCCESSFUL = "Comment was created successfully";
    private final static String DELETE_SUCCESSFUL = "Comment was deleted successfully.";

    private final static String INVALID_USER = "Invalid user.";
    private final static String INVALID_POST_USER = "Post's user is incorrect.";

    public CommentServiceImpl(CommentMapper commentMapper, UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository) {
        this.commentMapper = commentMapper;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public ResponseEntity<?> saveComment(Long channelId, Long postId, CommentRequest commentRequest, String username) {

        Post post = getById(postId);

        if (!post.getUser().getId().equals(channelId)){
            return ResponseEntity.badRequest().body(new MessageResponse(INVALID_POST_USER));
        }

        User commentator = userRepository.getByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        Comment comment = commentMapper.toEntity(commentRequest);
        comment.setPost(post);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUser(commentator);
        commentRepository.save(comment);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MessageResponse(CREATE_SUCCESSFUL));
    }

    @Override
    public ResponseEntity<?> deleteComment(Long channelId, Long postId, Long commentId, String username) {

        Post post = getById(postId);

        if(!post.getUser().getId().equals(channelId)){
            return ResponseEntity.badRequest().body(new MessageResponse(INVALID_POST_USER));
        }

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

        if(!comment.getUser().getUsername().equals(username)){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(INVALID_USER));
        }

        commentRepository.delete(comment);

        return ResponseEntity
                .ok()
                .body(new MessageResponse(DELETE_SUCCESSFUL));
    }

    @Override
    public ResponseEntity<?> getAllByPostId(Long channelId, Long postId) {

        if(userRepository.getById(channelId).isEmpty()){
            return ResponseEntity.badRequest().body(new MessageResponse(INVALID_POST_USER));
        }

        List<CommentResponse> commentList = commentRepository
                .getAllByPostId(postId)
                .stream().map(commentMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(commentList);
    }

    private Post getById(Long id){
        return postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
    }
}
