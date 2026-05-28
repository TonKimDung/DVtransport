package com.transport.backend.dto.driver_license;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DriverLicenseRequest {
    private Integer driverId;
    private String licenseNumber;
    private String licenseClass;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String fileUrl;
    private String status;
}