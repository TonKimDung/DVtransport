package com.transport.backend.entity;

import java.math.BigDecimal;
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
@Table(name = "fuel_transactions")
public class FuelTransaction {

    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id")
    private Partner partner;

    @Column(name = "fuel_date")
    private LocalDateTime fuelDate;

    @Column(name = "quantity_liters", precision = 15, scale = 2)
    private BigDecimal quantityLiters;

    @Column(name = "unit_price", precision = 15, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "total_amount", precision = 15, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "invoice_number", length = 255)
    private String invoiceNumber;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public FuelTransaction() {
    }

    public FuelTransaction( Vehicle vehicle, Trip trip, Driver driver, Partner partner,
            LocalDateTime fuelDate, BigDecimal quantityLiters, BigDecimal unitPrice, BigDecimal totalAmount,
            String invoiceNumber, LocalDateTime createdAt) {
                
        this.vehicle = vehicle;
        this.trip = trip;
        this.driver = driver;
        this.partner = partner;
        this.fuelDate = fuelDate;
        this.quantityLiters = quantityLiters;
        this.unitPrice = unitPrice;
        this.totalAmount = totalAmount;
        this.invoiceNumber = invoiceNumber;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    
    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
    
    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
    
    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }
    
    public LocalDateTime getFuelDate() {
        return fuelDate;
    }

    public void setFuelDate(LocalDateTime fuelDate) {
        this.fuelDate = fuelDate;
    }
    
    public BigDecimal getQuantityLiters() {
        return quantityLiters;
    }

    public void setQuantityLiters(BigDecimal quantityLiters) {
        this.quantityLiters = quantityLiters;
    }
    
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}