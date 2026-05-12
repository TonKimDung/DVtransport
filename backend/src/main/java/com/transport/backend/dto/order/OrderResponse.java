package com.transport.backend.dto.order;

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
public class OrderResponse {
    private Integer id;
    private String orderCode;

    private Integer customerId;
    private String customerName;

    private Integer contractId;
    private Integer routeId;
    private String routeName;

    private String cargoType;
    private BigDecimal weight;
    private BigDecimal quantity;
    private String pickupAddress;
    private String deliveryAddress;
    private BigDecimal totalAmount;
    private String status;
    private LocalDateTime createdAt;
}
