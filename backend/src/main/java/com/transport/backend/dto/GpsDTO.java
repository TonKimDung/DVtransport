package com.transport.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class GpsDTO {

    private Integer vehicleId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private LocalDateTime recordedAt;

    public GpsDTO() {
    }

    public GpsDTO(Integer vehicleId, Double latitude, Double longitude, LocalDateTime recordedAt) {
        this.vehicleId = vehicleId;
        this.latitude = BigDecimal.valueOf(latitude);
        this.longitude = BigDecimal.valueOf(longitude);
        this.recordedAt = recordedAt;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public Double getLatitude() {
        return latitude.doubleValue();
    }

    public Double getLongitude() {
        return longitude.doubleValue();
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }
}