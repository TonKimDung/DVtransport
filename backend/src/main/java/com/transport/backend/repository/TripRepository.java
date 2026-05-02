package com.transport.backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transport.backend.entity.Trip;

public interface TripRepository extends JpaRepository<Trip, Integer> {
    List<Trip> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    List<Trip> findByStatus(String status);

    boolean existsByTripCode(String tripCode);
}