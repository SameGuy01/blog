package ru.andreev.blog.domain.dto.response;

import lombok.Data;

@Data
public class AuthorResponse {

    private String username;

    private String firstname;

    private String lastname;
}
