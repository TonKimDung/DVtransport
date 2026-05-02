package com.transport.backend.dto.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private Integer userId;
    private String username;
    private String fullName;
    private String email;
    private String roleName;
    private Boolean isActive;
    private String message;
}
