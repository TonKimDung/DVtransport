package com.transport.backend.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transport.backend.entity.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    Optional<Vehicle> findByPlateNumber(String plateNumber);
    List<Vehicle> findByCapacityGreaterThanEqualAndStatus(BigDecimal capacity, String status);
}
