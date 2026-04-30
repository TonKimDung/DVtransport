package com.transport.backend.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@Table(name = "vehicles")
public class Vehicle {

    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;

    @Column(name = "plate_number", nullable = false, unique = true, length = 255)
    private String plateNumber;

    @Column(name = "vehicle_type", length = 255)
    private String vehicleType;

    @Column(precision = 15, scale = 2)
    private BigDecimal capacity;

    @Column(length = 255)
    private String status;

    @Column(name = "manufacture_year")
    private Integer manufactureYear;

    @Column(name = "inspection_expiry")
    private LocalDate inspectionExpiry;

    @Column(name = "insurance_expiry")
    private LocalDate insuranceExpiry;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public Vehicle() {
    }

    public Vehicle( String plateNumber, String vehicleType, BigDecimal capacity, String status,
            Integer manufactureYear, LocalDate inspectionExpiry, LocalDate insuranceExpiry, LocalDateTime createdAt) {
        
        this.plateNumber = plateNumber;
        this.vehicleType = vehicleType;
        this.capacity = capacity;
        this.status = status;
        this.manufactureYear = manufactureYear;
        this.inspectionExpiry = inspectionExpiry;
        this.insuranceExpiry = insuranceExpiry;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }
    
    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
    
    public BigDecimal getCapacity() {
        return capacity;
    }

    public void setCapacity(BigDecimal capacity) {
        this.capacity = capacity;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public Integer getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(Integer manufactureYear) {
        this.manufactureYear = manufactureYear;
    }
    
    public LocalDate getInspectionExpiry() {
        return inspectionExpiry;
    }

    public void setInspectionExpiry(LocalDate inspectionExpiry) {
        this.inspectionExpiry = inspectionExpiry;
    }
    
    public LocalDate getInsuranceExpiry() {
        return insuranceExpiry;
    }

    public void setInsuranceExpiry(LocalDate insuranceExpiry) {
        this.insuranceExpiry = insuranceExpiry;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}