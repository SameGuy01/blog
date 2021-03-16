package ru.asteac.blog.usermanagment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.asteac.blog.domain.model.entity.Role;
import ru.asteac.blog.domain.model.enums.ERole;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> getByRole(ERole role);
}
