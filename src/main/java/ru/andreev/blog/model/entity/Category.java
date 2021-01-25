package ru.andreev.blog.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "category")
@NoArgsConstructor
public class Category extends AbstractEntity{

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "info", nullable = false)
    private String info;

    @OneToMany(mappedBy = "category")
    private List<Post> postList;
}
