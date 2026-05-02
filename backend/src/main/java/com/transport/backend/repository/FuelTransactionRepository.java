package com.transport.backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transport.backend.entity.FuelTransaction;

public interface FuelTransactionRepository extends JpaRepository<FuelTransaction, Integer> {

    List<FuelTransaction> findByVehicleId(Integer vehicleId);

    List<FuelTransaction> findByVehicleIdAndFuelDateBetween(
            Integer vehicleId,
            LocalDateTime start,
            LocalDateTime end
    );

    List<FuelTransaction> findByFuelDateBetween(
            LocalDateTime start,
            LocalDateTime end
    );
}