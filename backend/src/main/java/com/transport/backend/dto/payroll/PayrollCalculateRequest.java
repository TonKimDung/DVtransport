package com.transport.backend.dto.payroll;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PayrollCalculateRequest {
    private Integer driverId;
    private Integer month;
    private Integer year;
    private BigDecimal bonusAmount;
    private BigDecimal penaltyAmount;
}   