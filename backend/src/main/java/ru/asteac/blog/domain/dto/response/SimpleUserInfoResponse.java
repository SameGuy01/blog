package ru.asteac.blog.domain.dto.response;

import lombok.Data;

@Data
public class SimpleUserInfoResponse {
    private String id;
    private String username;
    private String firstname;
    private String lastname;
}
