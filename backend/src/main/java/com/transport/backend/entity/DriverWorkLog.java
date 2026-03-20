package com.transport.backend.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "driver_work_logs")
public class DriverWorkLog {

    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @Column(name = "work_date")
    private LocalDate workDate;

    @Column(name = "driving_hours", precision = 15, scale = 2)
    private BigDecimal drivingHours;

    @Column(name = "trip_count")
    private Integer tripCount;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public DriverWorkLog() {
    }

    public DriverWorkLog( Driver driver, Trip trip, LocalDate workDate, BigDecimal drivingHours,
            Integer tripCount, LocalDateTime createdAt) {
        
        this.driver = driver;
        this.trip = trip;
        this.workDate = workDate;
        this.drivingHours = drivingHours;
        this.tripCount = tripCount;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
    
    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
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
    
    public Integer getTripCount() {
        return tripCount;
    }

    public void setTripCount(Integer tripCount) {
        this.tripCount = tripCount;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}