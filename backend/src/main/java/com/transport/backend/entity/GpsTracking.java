package com.transport.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class GpsTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Trip trip;

    @ManyToOne
    private Vehicle vehicle;

    private BigDecimal latitude;
    private BigDecimal longitude;

    private LocalDateTime recordedAt;
}