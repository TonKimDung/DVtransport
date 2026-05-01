package com.transport.backend.dto.driver_assignment;

public class DriverWorkResponse {

    private Integer driverId;
    private double totalHours;
    private boolean overworked;

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(double totalHours) {
        this.totalHours = totalHours;
    }

    public boolean isOverworked() {
        return overworked;
    }

    public void setOverworked(boolean overworked) {
        this.overworked = overworked;
    }
}