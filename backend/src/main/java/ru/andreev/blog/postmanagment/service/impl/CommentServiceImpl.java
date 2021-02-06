package ru.andreev.blog.postmanagment.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andreev.blog.domain.dto.request.CommentRequest;
import ru.andreev.blog.domain.mapper.CommentMapper;
import ru.andreev.blog.domain.model.entity.Comment;
import ru.andreev.blog.domain.model.entity.Post;
import ru.andreev.blog.domain.model.entity.User;
import ru.andreev.blog.postmanagment.exception.PostNotFoundException;
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

    public CommentServiceImpl(CommentMapper commentMapper, UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository) {
        this.commentMapper = commentMapper;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public ResponseEntity<?> saveComment(CommentRequest commentRequest, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        Post post = postRepository.findById(Long.valueOf(commentRequest.getPostId()))
                .orElseThrow(() -> new PostNotFoundException(commentRequest.getPostId()));

        Comment comment = commentMapper.toEntity(commentRequest);
        comment.setPost(post);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setCommentator(user);
        commentRepository.save(comment);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
