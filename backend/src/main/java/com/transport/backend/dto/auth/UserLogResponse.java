package com.transport.backend.dto.auth;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLogResponse {
    private Integer id;
    private Integer userId;
    private String username;
    private String fullName;
    private String action;
    private LocalDateTime createdAt;
}