package com.transport.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transport.backend.entity.Incident;

public interface IncidentRepository extends JpaRepository<Incident, Integer> {
    List<Incident> findByStatus(String status);
    List<Incident> findByVehicleId(Integer vehicleId);
    List<Incident> findByDriverId(Integer driverId);
}