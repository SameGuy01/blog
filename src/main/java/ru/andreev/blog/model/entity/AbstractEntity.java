package ru.andreev.blog.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Getter
@Setter
@MappedSuperclass
@GenericGenerator(
        name = "abstract_entity_generator",
        strategy = "enhanced-sequence",
        parameters = {
                @Parameter(name = "optimizer", value = "pooled-lo"),
                @Parameter(name = "initial_value", value = "1"),
                @Parameter(name = "increment_size", value = "1"),
                @Parameter(name = "sequence_name", value = "ccs_session_sqc")
        })
@NoArgsConstructor
@AllArgsConstructor
public class AbstractEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy =  GenerationType.SEQUENCE,
            generator = "abstract_entity_generator")
    private Long id;

}
