package com.transport.backend.dto.payroll;

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
public class PayrollResponse {
    private Integer id;
    private Integer driverId;
    private String driverName;
    private Integer month;
    private Integer year;

    private BigDecimal baseSalary;
    private BigDecimal completedOrderAmount;
    private BigDecimal commissionAmount;
    private BigDecimal commissionRate;

    private BigDecimal totalSalary;
    private BigDecimal bonusAmount;
    private BigDecimal penaltyAmount;
    private BigDecimal finalAmount;

    private String status;
    private LocalDateTime createdAt;
}