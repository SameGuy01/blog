package ru.asteac.blog.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SimplePostResponse {

    private String id;

    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    private SimpleUserInfoResponse user;

    private Integer likeUsers;
}
