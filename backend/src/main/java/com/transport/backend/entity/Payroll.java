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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@Table(name = "payrolls")
public class Payroll {

    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @Column(name = "month")
    private Integer month;

    @Column(name = "year")
    private Integer year;

    @Column(name = "total_salary", precision = 15, scale = 2)
    private BigDecimal totalSalary;

    @Column(name = "bonus_amount", precision = 15, scale = 2)
    private BigDecimal bonusAmount;

    @Column(name = "penalty_amount", precision = 15, scale = 2)
    private BigDecimal penaltyAmount;

    @Column(name = "final_amount", precision = 15, scale = 2)
    private BigDecimal finalAmount;

    @Column(length = 255)
    private String status;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public Payroll() {
    }

    public Payroll( Driver driver, Integer month, Integer year, BigDecimal totalSalary,
            BigDecimal bonusAmount, BigDecimal penaltyAmount, BigDecimal finalAmount, String status,
            LocalDateTime createdAt) {
       
        this.driver = driver;
        this.month = month;
        this.year = year;
        this.totalSalary = totalSalary;
        this.bonusAmount = bonusAmount;
        this.penaltyAmount = penaltyAmount;
        this.finalAmount = finalAmount;
        this.status = status;
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
    
    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }
    
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
    
    public BigDecimal getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(BigDecimal totalSalary) {
        this.totalSalary = totalSalary;
    }
    
    public BigDecimal getBonusAmount() {
        return bonusAmount;
    }

    public void setBonusAmount(BigDecimal bonusAmount) {
        this.bonusAmount = bonusAmount;
    }
    
    public BigDecimal getPenaltyAmount() {
        return penaltyAmount;
    }

    public void setPenaltyAmount(BigDecimal penaltyAmount) {
        this.penaltyAmount = penaltyAmount;
    }
    
    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}