package ru.andreev.blog.domain.dto.response;

import lombok.Data;

@Data
public class UserInfoResponse {

    private String id;

    private String firstname;

    private String lastname;

    private String username;

    private String email;

    private String about;

}
