package com.transport.backend.repository.driver_assignment;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transport.backend.entity.VehicleDriverAssignment;

public interface VehicleDriverAssignmentRepository
                extends JpaRepository<VehicleDriverAssignment, Integer> {

        Optional<VehicleDriverAssignment> findTopByVehicle_IdOrderByAssignedDateDesc(Integer vehicleId);

        List<VehicleDriverAssignment> findByStatus(String status);
        List<VehicleDriverAssignment> findByDriver_IdOrderByAssignedDateDesc(Integer driverId);

        
}
