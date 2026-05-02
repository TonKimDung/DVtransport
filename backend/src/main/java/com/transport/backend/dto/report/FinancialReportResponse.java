package com.transport.backend.dto.report;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinancialReportResponse {
    private Integer month;
    private Integer year;

    private BigDecimal totalFuelCost;
    private BigDecimal totalTripExpense;
    private BigDecimal totalPayroll;
    private BigDecimal totalCost;

    private BigDecimal totalRevenue;
    private BigDecimal profit;
}