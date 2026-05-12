package com.transport.backend.dto.auth;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Integer id;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private Integer roleId;
    private String roleName;
    private Boolean isActive;
    private LocalDateTime createdAt;
}
