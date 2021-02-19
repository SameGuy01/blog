package ru.andreev.blog.domain.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentRequest {

    @NotBlank(message = "Content must be not empty ")
    private String content;

}
