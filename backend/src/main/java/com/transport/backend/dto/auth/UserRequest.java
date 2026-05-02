package com.transport.backend.dto.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private Integer roleId;
    private Boolean isActive;
}
