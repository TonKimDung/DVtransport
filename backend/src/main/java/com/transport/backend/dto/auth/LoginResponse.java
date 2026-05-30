package com.transport.backend.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String token;
    private Integer driverId;

    private Integer userId;
    private String username;
    private String fullName;
    private String email;
    private String roleName;
    private Boolean isActive;

    private String message;
}
