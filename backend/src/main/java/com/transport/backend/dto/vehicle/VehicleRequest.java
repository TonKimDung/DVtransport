package com.transport.backend.dto.vehicle;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRequest {
    private String plateNumber;
    private String vehicleType;
    private BigDecimal capacity;
    private String status;
    private Integer manufactureYear;
    private LocalDate inspectionExpiry;
    private LocalDate insuranceExpiry;
}