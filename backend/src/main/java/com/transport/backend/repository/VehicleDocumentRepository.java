package com.transport.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transport.backend.entity.VehicleDocument;

public interface VehicleDocumentRepository extends JpaRepository<VehicleDocument, Integer> {
    List<VehicleDocument> findByVehicleId(Integer vehicleId);
}