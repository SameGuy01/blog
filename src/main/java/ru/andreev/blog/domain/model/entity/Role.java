package ru.andreev.blog.domain.model.entity;

import lombok.Getter;
import lombok.Setter;
import ru.andreev.blog.domain.model.enums.ERole;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private ERole role;

}
