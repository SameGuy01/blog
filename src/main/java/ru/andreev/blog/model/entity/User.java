package ru.andreev.blog.model.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@Table(name = "user")
public class User extends AbstractEntity {

    @Column(name = "firstname", nullable = false)
    private String firstName;

    @Column(name = "lastname", nullable = false)
    private String lastName;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "registered_at", nullable = false)
    private LocalDateTime registeredAt;

    @OneToMany(mappedBy = "post")
    private List<Post> postList;

    @Column(name = "about")
    private String about;
}
