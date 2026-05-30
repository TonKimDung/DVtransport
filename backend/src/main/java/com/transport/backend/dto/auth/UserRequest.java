package com.transport.backend.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    // dùng khi tạo tài khoản cho tài xế
    private Integer driverId;
}