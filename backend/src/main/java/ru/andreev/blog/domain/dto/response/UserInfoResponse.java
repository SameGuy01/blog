package ru.andreev.blog.domain.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class UserInfoResponse {

    private String id;

    private String firstname;

    private String lastname;

    private String username;

    private String email;

    private String about;

    private List<SimpleUserInfoResponse> subscribers;

    private List<SimpleUserInfoResponse> subscriptions;
}
