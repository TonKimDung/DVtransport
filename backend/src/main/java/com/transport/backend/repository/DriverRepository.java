package com.transport.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.transport.backend.entity.Driver;
import com.transport.backend.entity.VehicleDriverAssignment;

public interface DriverRepository extends JpaRepository<Driver, Integer> {
    boolean existsByLicenseNumber(String licenseNumber);

    public interface VehicleDriverAssignmentRepository
            extends JpaRepository<VehicleDriverAssignment, Integer> {

        List<VehicleDriverAssignment> findByStatus(String status);
    }
}