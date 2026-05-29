package com.transport.backend.dto.contract;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ContractResponse {

    public Integer id;

    public String contractNumber;
    public String contractType;

    public String customerName;
    public String partnerName;

    // DRIVER
    public String driverName;

    // LICENSE
    public String licenseNumber;

    public LocalDate startDate;
    public LocalDate endDate;

    public BigDecimal totalValue;
    public BigDecimal baseSalary;

    public String status;
}