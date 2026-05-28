package com.transport.backend.dto.contract;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ContractDTO {

    private Integer id;

    private String contractNumber;

    private String contractType;

    private Integer driverId;

    private String driverName;

    private Integer driverLicenseId;

    private String licenseClass;

    private BigDecimal baseSalary;

    private LocalDate startDate;

    private LocalDate endDate;

    private BigDecimal totalValue;

    private String status;

    private LocalDateTime createdAt;

    // getter setter
}