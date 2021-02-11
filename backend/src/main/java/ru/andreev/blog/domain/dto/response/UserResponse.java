package ru.andreev.blog.domain.dto.response;

import lombok.Data;

@Data
public class UserResponse {

    private String id;

    private String username;

    private String firstname;

    private String lastname;
}
