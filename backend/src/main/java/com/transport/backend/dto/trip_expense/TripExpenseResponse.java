package com.transport.backend.dto.trip_expense;

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
public class TripExpenseResponse {
    private Integer id;
    private Integer tripId;
    private String tripCode;
    private String expenseType;
    private BigDecimal amount;
    private String description;
    private LocalDateTime createdAt;
}
