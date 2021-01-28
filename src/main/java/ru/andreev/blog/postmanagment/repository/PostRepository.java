package ru.andreev.blog.postmanagment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.andreev.blog.domain.model.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
