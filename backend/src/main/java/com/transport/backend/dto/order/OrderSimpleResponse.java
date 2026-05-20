package com.transport.backend.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSimpleResponse {

    private Integer id;

    private String orderCode;

    private String cargoType;

    private BigDecimal weight;

    private BigDecimal quantity;

    private String pickupAddress;

    private String deliveryAddress;

    private String status;

    private Integer routeId;

    private String routeName;

    private LocalDateTime createdAt;
}