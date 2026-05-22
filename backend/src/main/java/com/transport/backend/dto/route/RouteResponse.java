package com.transport.backend.dto.route;

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
public class RouteResponse {
    private Integer id;
    private String routeName;
    private String startLocation;
    private String endLocation;
    private BigDecimal distanceKm;
    private BigDecimal estimatedHours;
    private BigDecimal costPerTon;
}
