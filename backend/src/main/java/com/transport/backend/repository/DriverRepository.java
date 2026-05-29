package com.transport.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.transport.backend.dto.driver.DriverDTO;
import com.transport.backend.entity.Driver;
import com.transport.backend.entity.VehicleDriverAssignment;

public interface DriverRepository extends JpaRepository<Driver, Integer> {
    boolean existsByLicenseNumber(String licenseNumber);

    public interface VehicleDriverAssignmentRepository
            extends JpaRepository<VehicleDriverAssignment, Integer> {

        List<VehicleDriverAssignment> findByStatus(String status);
    }

    @Query("""
                SELECT d
                FROM Driver d
                WHERE d.id NOT IN (
                    SELECT c.driver.id
                    FROM Contract c
                    WHERE c.driver IS NOT NULL
                      AND c.status = 'ACTIVE'
                )
            """)
    List<Driver> findDriversWithoutContract();
}