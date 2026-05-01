package com.transport.backend.repository.driver_assignment;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transport.backend.entity.VehicleDriverAssignment;

public interface VehicleDriverAssignmentRepository
        extends JpaRepository<VehicleDriverAssignment, Integer> {
}
