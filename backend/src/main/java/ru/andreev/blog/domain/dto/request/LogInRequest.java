package ru.andreev.blog.domain.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Data
public class LogInRequest {

    @NotBlank(message = "Username must be not empty")
    private String username;

    @NotBlank(message = "Password must be not empty")
    private String password;
}
