package ru.andreev.blog.usermanagment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.andreev.blog.domain.model.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> getById(Long id);

    Optional<User> getByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
