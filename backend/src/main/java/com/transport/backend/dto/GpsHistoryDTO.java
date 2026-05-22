package com.transport.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class GpsHistoryDTO {
    private Integer id;
    private Integer tripId;
    private Integer vehicleId;
    private Double lat;
    private Double lng;
    private LocalDateTime recordedAt;
}