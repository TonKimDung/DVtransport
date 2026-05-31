package com.transport.backend.dto.trip;

import java.math.BigDecimal;

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
public class TripSalaryResponse {
    private Integer tripId;
    private String tripCode;
    private BigDecimal totalOrderAmount;
    private BigDecimal salaryAmount;
    private String note;
}