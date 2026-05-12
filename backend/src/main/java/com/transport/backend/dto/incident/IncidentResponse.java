package com.transport.backend.dto.incident;

import java.time.LocalDateTime;

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
public class IncidentResponse {
    private Integer id;

    private Integer tripId;
    private String tripCode;

    private Integer vehicleId;
    private String plateNumber;

    private Integer driverId;
    private String driverName;

    private String incidentType;
    private String description;
    private LocalDateTime incidentTime;
    private String status;
    private LocalDateTime createdAt;
}
