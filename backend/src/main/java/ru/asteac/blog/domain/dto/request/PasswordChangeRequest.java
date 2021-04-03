package ru.asteac.blog.domain.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class PasswordChangeRequest {

    @NotBlank(message = "Password can't be empty")
    @Size(min = 6, max = 40, message = "Password size must be between 6 and 40")
    private String oldPassword;

    @NotBlank(message = "New password can't be empty")
    @Size(min = 6, max = 40, message = "Password size must be between 6 and 40")
    private String newPassword;

    @NotBlank(message = "Matching password can't be empty")
    @Size(min = 6, max = 40, message = "Password size must be between 6 and 40")
    private String matchingPassword;

}
