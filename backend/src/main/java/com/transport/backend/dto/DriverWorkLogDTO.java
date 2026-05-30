package com.transport.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DriverWorkLogDTO {

    private Integer id;
    private Integer driverId;
    private Integer tripId;
    private LocalDate workDate;
    private BigDecimal drivingHours;
    private Boolean overtime;
    private String warningLevel;
    private String warningMessage;

    public DriverWorkLogDTO() {
    }

    public DriverWorkLogDTO(Integer id,
            Integer driverId,
            Integer tripId,
            LocalDate workDate,
            BigDecimal drivingHours,
            Boolean overtime,
            String warningLevel,
            String warningMessage) {
        this.id = id;
        this.driverId = driverId;
        this.tripId = tripId;
        this.workDate = workDate;
        this.drivingHours = drivingHours;
        this.overtime = overtime;
        this.warningLevel = warningLevel;
        this.warningMessage = warningMessage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public LocalDate getWorkDate() {
        return workDate;
    }

    public void setWorkDate(LocalDate workDate) {
        this.workDate = workDate;
    }

    public BigDecimal getDrivingHours() {
        return drivingHours;
    }

    public void setDrivingHours(BigDecimal drivingHours) {
        this.drivingHours = drivingHours;
    }

    public Boolean getOvertime() {
        return overtime;
    }

    public void setOvertime(Boolean overtime) {
        this.overtime = overtime;
    }

    public String getWarningLevel() {
        return warningLevel;
    }

    public void setWarningLevel(String warningLevel) {
        this.warningLevel = warningLevel;
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }

    // getters + setters
}