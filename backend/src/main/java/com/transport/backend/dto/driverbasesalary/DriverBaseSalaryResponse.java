package com.transport.backend.dto.driverbasesalary;

import java.math.BigDecimal;
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
public class DriverBaseSalaryResponse {
    private Integer id;

    private Integer driverId;
    private String driverName;

    private Integer driverLicenseId;
    private String licenseNumber;
    private String licenseClass;

    private BigDecimal baseSalary;
    private String status;
    private LocalDateTime createdAt;
}