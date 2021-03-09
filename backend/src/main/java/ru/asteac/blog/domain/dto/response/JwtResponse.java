package ru.asteac.blog.domain.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {

    private String id;
    private String token;
    @Builder.Default
    private String type = "Bearer";
    private String username;
    private String email;
    private List<String> roles;
    private Boolean isActive;


}
