package ru.andreev.blog.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
public class Category extends AbstractEntity{
    @Column(name = "title", nullable = false)
    private String title;

    @OneToMany( fetch = FetchType.LAZY, mappedBy = "category")
    private List<Post> postList;
}