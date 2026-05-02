package com.transport.backend.dto.trip_expense;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TripExpenseRequest {
    private Integer tripId;
    private String expenseType;
    private BigDecimal amount;
    private String description;
}