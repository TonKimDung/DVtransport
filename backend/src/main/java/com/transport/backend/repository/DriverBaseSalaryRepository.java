package com.transport.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transport.backend.entity.DriverBaseSalary;

public interface DriverBaseSalaryRepository extends JpaRepository<DriverBaseSalary, Integer> {
    List<DriverBaseSalary> findByDriverId(Integer driverId);

    Optional<DriverBaseSalary> findTopByDriverIdAndStatusOrderByCreatedAtDesc(
            Integer driverId,
            String status
    );
}