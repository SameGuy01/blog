package ru.andreev.blog.domain.dto.response;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class CategoryResponse {

    @NotBlank
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private List<PostResponse> postList;
}
