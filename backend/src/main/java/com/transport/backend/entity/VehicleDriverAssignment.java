package com.transport.backend.entity;

import java.time.LocalDate;

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
@Table(name = "vehicle_driver_assignments")
public class VehicleDriverAssignment {

    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @Column(name = "assigned_date")
    private LocalDate assignedDate;

    @Column(columnDefinition = "TEXT")
    private String note;

    public VehicleDriverAssignment() {
    }

    public VehicleDriverAssignment( Vehicle vehicle, Driver driver, LocalDate assignedDate, String note) {
        
        this.vehicle = vehicle;
        this.driver = driver;
        this.assignedDate = assignedDate;
        this.note = note;
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
    
    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
    
    public LocalDate getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(LocalDate assignedDate) {
        this.assignedDate = assignedDate;
    }
    
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}