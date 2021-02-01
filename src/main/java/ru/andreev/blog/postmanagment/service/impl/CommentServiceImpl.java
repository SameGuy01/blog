package ru.andreev.blog.postmanagment.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andreev.blog.domain.dto.request.CommentRequest;
import ru.andreev.blog.domain.mapper.CommentMapper;
import ru.andreev.blog.domain.model.entity.Comment;
import ru.andreev.blog.domain.model.entity.User;
import ru.andreev.blog.postmanagment.repository.CommentRepository;
import ru.andreev.blog.postmanagment.service.CommentService;
import ru.andreev.blog.usermanagment.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentMapper commentMapper, UserRepository userRepository, CommentRepository commentRepository) {
        this.commentMapper = commentMapper;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public ResponseEntity<?> saveComment(CommentRequest commentRequest, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        Comment comment = commentMapper.toEntity(commentRequest);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setCommentator(user);

        commentRepository.save(comment);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
