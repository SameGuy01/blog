package ru.asteac.blog.domain.dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class SignUpRequest {

    @NotBlank(message = "Username must not be empty")
    @Size(min = 3, max = 20, message = "Username size must be between 3 and 20")
    private String username;

    @NotBlank(message = "Email must not be empty")
    @Size(max = 50)
    private String email;

    @NotBlank(message = "Password must not be empty")
    @Size(min = 6, max = 40, message = "Password size must be between 6 and 40")
    private String password;

    @NotBlank(message = "Username must not be empty")
    private String firstname;

    @NotBlank(message = "Lastname must not be empty")
    private String lastname;

    private Set<String> role;
}
