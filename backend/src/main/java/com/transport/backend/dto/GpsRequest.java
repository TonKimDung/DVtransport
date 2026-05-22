package com.transport.backend.dto;

import lombok.Data;

@Data
public class GpsRequest {
    private Integer tripId;
    private Double lat;
    private Double lng;
}