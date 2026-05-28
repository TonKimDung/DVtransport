package com.transport.backend.dto.driverbasesalary;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DriverBaseSalaryRequest {
    private Integer driverId;
    private Integer driverLicenseId;
    private BigDecimal baseSalary;
    private String status;
}