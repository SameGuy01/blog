package ru.asteac.blog.domain.model.entity;

import lombok.Getter;
import lombok.Setter;
import ru.asteac.blog.domain.model.enums.ERole;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private ERole role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Role role1 = (Role) o;
        return role == role1.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), role);
    }
}
