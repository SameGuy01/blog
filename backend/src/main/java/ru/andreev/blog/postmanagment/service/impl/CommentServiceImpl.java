package ru.andreev.blog.postmanagment.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andreev.blog.domain.dto.request.CommentRequest;
import ru.andreev.blog.domain.dto.response.MessageResponse;
import ru.andreev.blog.domain.mapper.CommentMapper;
import ru.andreev.blog.domain.model.entity.Comment;
import ru.andreev.blog.domain.model.entity.Post;
import ru.andreev.blog.domain.model.entity.User;
import ru.andreev.blog.postmanagment.exception.CommentNotFoundException;
import ru.andreev.blog.postmanagment.exception.PostNotFoundException;
import ru.andreev.blog.postmanagment.exception.UserNotFoundException;
import ru.andreev.blog.postmanagment.repository.CommentRepository;
import ru.andreev.blog.postmanagment.repository.PostRepository;
import ru.andreev.blog.postmanagment.service.CommentService;
import ru.andreev.blog.usermanagment.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private final static String DELETE_SUCCESSFUL = "Comment was deleted successfully.";

    private final static String INVALID_USER = "Invalid user.";

    public CommentServiceImpl(CommentMapper commentMapper, UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository) {
        this.commentMapper = commentMapper;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public ResponseEntity<?> saveComment(Long postId, CommentRequest commentRequest, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        Post post = postRepository.findById(postId).
                orElseThrow(PostNotFoundException::new);

        Comment comment = commentMapper.toEntity(commentRequest);
        comment.setPost(post);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setCommentator(user);
        commentRepository.save(comment);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentMapper.toDto(comment));
    }

    @Override
    public ResponseEntity<?> deleteComment(Long postId, Long commentId, String username) {

        Comment comment = commentRepository.getById(postId)
                .orElseThrow(CommentNotFoundException::new);

        if(!comment.getCommentator().getUsername().equals(username)){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(INVALID_USER));
        }

        commentRepository.delete(comment);

        return ResponseEntity
                .ok()
                .body(new MessageResponse(DELETE_SUCCESSFUL));
    }
}
