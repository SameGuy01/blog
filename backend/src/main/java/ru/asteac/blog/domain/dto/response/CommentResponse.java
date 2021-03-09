package ru.asteac.blog.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponse {

    private String id;

    private String content;

    private UserInfoResponse user;

    private String postId;

    private CommentResponse child;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;
}
