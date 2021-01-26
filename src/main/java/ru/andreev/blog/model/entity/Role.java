package ru.andreev.blog.model.entity;

import lombok.Getter;
import lombok.Setter;
import ru.andreev.blog.model.enums.ERole;

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
