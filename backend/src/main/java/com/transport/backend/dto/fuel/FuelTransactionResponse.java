package com.transport.backend.dto.fuel;

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
public class FuelTransactionResponse {
    private Integer id;

    private Integer vehicleId;
    private String plateNumber;

    private Integer tripId;
    private Integer driverId;
    private String driverName;


    private LocalDateTime fuelDate;
    private BigDecimal quantityLiters;
    private BigDecimal unitPrice;
    private BigDecimal totalAmount;
    private String invoiceNumber;
    private LocalDateTime createdAt;
}
