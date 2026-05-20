package com.transport.backend.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transport.backend.entity.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    boolean existsByPlateNumber(String plateNumber);

    // =====================================================
    // FIND VEHICLE BY STATUS
    // =====================================================

    List<Vehicle> findByStatus(String status);

    // =====================================================
    // FIND VEHICLE BY CAPACITY + STATUS
    // =====================================================

    List<Vehicle> findByCapacityGreaterThanEqualAndStatus(
            BigDecimal capacity,
            String status);

    // =====================================================
    // FIND VEHICLE BY STATUS + CAPACITY
    // =====================================================

    List<Vehicle> findByStatusAndCapacityGreaterThanEqual(
            String status,
            BigDecimal capacity);

    Optional<Vehicle> findByPlateNumber(String plateNumber);
}