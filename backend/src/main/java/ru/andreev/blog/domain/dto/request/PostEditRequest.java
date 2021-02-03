package ru.andreev.blog.domain.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PostEditRequest {

    @NotBlank
    private String id;

    @NotBlank
    private String content;

    @NotBlank
    private String categoryId;

    @NotBlank
    private String userId;
}
