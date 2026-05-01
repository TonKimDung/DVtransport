package com.transport.backend.dto.order;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private String orderCode;
    private Integer customerId;
    private Integer contractId;
    private Integer routeId;
    private String cargoType;
    private BigDecimal weight;
    private BigDecimal quantity;
    private String pickupAddress;
    private String deliveryAddress;
    private BigDecimal totalAmount;
    private String status;
}