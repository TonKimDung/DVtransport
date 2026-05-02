package com.transport.backend.dto.incident;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncidentRequest {
    private Integer tripId;
    private Integer vehicleId;
    private Integer driverId;
    private String incidentType;
    private String description;
    private LocalDateTime incidentTime;
    private String status;
}
