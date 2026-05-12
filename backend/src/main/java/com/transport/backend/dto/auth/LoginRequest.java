package com.transport.backend.dto.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    private String username;
    private String password;
}