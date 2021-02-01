package ru.andreev.blog.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostResponse {

    private String id;

    private String content;

    private AuthorResponse user;

    private SimpleCategoryResponse category;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    private List<CommentResponse> commentList;
}
