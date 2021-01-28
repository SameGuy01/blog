package ru.andreev.blog.domain.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PostRequest {

    @NotBlank
    private String content;

    @NotBlank
    private Long categoryId;
}
