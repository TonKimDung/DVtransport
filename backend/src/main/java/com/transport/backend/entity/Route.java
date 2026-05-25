package com.transport.backend.entity;

import java.math.BigDecimal;

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
@Table(name = "routes")
public class Route {

    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;

    @Column(name = "route_name", length = 255)
    private String routeName;

    @Column(name = "start_location", columnDefinition = "TEXT")
    private String startLocation;

    @Column(name = "end_location", columnDefinition = "TEXT")
    private String endLocation;

    @Column(name = "distance_km", precision = 15, scale = 2)
    private BigDecimal distanceKm;

    @Column(name = "estimated_hours", precision = 15, scale = 2)
    private BigDecimal estimatedHours;

    @Column(name = "cost_per_ton", precision = 15, scale = 2)
    private BigDecimal costPerTon;

    public Route() {
    }

    public Route( String routeName, String startLocation, String endLocation, BigDecimal distanceKm,
            BigDecimal estimatedHours) {
        
        this.routeName = routeName;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.distanceKm = distanceKm;
        this.estimatedHours = estimatedHours;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }
    
    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }
    
    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }
    
    public BigDecimal getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(BigDecimal distanceKm) {
        this.distanceKm = distanceKm;
    }
    
    public BigDecimal getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(BigDecimal estimatedHours) {
        this.estimatedHours = estimatedHours;
    }
}