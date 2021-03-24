package ru.asteac.blog.domain.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserInfoEditRequest {

    @NotBlank(message = "Id must be not empty")
    private String id;

    @NotBlank(message = "Firstname must be not empty")
    private String firstname;

    @NotBlank(message = "Lastname must be not empty")
    private String lastname;

    @NotBlank(message = "Username must be not empty")
    private String username;

    @NotBlank(message = "Email must be not empty")
    private String email;

    @NotBlank(message = "About must be not empty")
    private String about;

    @NotBlank(message = "Password can't be empty")
    private String password;
}
