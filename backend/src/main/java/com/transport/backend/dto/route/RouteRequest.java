package com.transport.backend.dto.route;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RouteRequest {
    private String routeName;
    private String startLocation;
    private String endLocation;
    private BigDecimal distanceKm;
    private BigDecimal estimatedHours;
    private BigDecimal costPerTon;
}
