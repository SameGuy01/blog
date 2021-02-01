package ru.andreev.blog.domain.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class CategoryResponse {

    private Long id;

    private String title;

    private List<SimplePostResponse> postList;
}
