package ru.asteac.blog.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "files")
@NoArgsConstructor
@AllArgsConstructor
public class File extends AbstractEntity {

    @Column(name = "file_path", nullable = false)
    private String filepath;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        File file = (File) o;
        return Objects.equals(filepath, file.filepath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), filepath);
    }
}