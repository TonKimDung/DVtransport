package com.transport.backend.dto.contract;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateContractRequest {

    private String contractNumber;

    private String contractType;

    private Integer customerId;

    private Integer partnerId;

    private Integer driverId;

    private Integer driverLicenseId;

    private LocalDate startDate;

    private LocalDate endDate;

    private BigDecimal totalValue;

    private String status;

    // getter setter
}