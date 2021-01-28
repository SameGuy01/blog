package ru.andreev.blog.domain.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CategoryRequest {

    @NotBlank
    private String title;
}