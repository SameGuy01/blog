package ru.asteac.blog.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.asteac.blog.domain.model.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostResponse {

    private String id;

    private String content;

    private UserInfoResponse user;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    private List<CommentResponse> commentList;

    private List<SimpleUserInfoResponse> likeUsers;
}
