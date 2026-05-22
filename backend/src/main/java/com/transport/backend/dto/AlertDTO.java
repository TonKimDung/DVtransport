package com.transport.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AlertDTO {

    private String type;

    private Integer tripId;

    private String message;

    private LocalDateTime createdAt;
}