package ru.andreev.blog.usermanagment.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LogInRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
