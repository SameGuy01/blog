package ru.asteac.blog.postmanagment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.asteac.blog.domain.model.entity.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends GenericJpaRepository<Comment> {

    List<Comment> getAllByPostId(Long postId);
}
