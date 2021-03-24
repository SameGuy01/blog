package ru.asteac.blog.domain.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PasswordChangeRequest {

    @NotBlank(message = "Password can't be empty")
    private String oldPassword;

    @NotBlank(message = "New password can't be empty")
    private String newPassword;

    @NotBlank(message = "Matching password can't be empty")
    private String matchingPassword;
}
