package com.transport.backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transport.backend.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
    List<Document> findByExpiryDateBetween(LocalDate startDate, LocalDate endDate);
    List<Document> findByDocumentType(String documentType);
    List<Document> findByVehicleId(Integer vehicleId);
    List<Document> findByDriverId(Integer driverId);
}