package com.transport.backend.dto.fuel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FuelTransactionRequest {
    private Integer vehicleId;
    private Integer tripId;
    private Integer driverId;
    private Integer partnerId;
    private LocalDateTime fuelDate;
    private BigDecimal quantityLiters;
    private BigDecimal unitPrice;
    private String invoiceNumber;
}
