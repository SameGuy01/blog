package ru.asteac.blog.domain.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class AbstractEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy =  GenerationType.SEQUENCE,
            generator = "abstract_entity_generator")
    private Long id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntity that = (AbstractEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
