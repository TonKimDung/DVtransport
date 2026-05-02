package com.transport.backend.dto.route;

import lombok.*;
import java.math.BigDecimal;

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
}
