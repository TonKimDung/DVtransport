package com.transport.backend.dto.driver_license;

import java.time.LocalDate;
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
public class DriverLicenseResponse {
    private Integer id;
    private Integer driverId;
    private String driverName;
    private String licenseNumber;
    private String licenseClass;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String fileUrl;
    private String status;
    private LocalDateTime createdAt;
}
