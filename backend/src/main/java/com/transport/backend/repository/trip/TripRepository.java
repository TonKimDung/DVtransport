package com.transport.backend.repository.trip;

import org.springframework.data.jpa.repository.JpaRepository;
import com.transport.backend.entity.Trip;

public interface TripRepository extends JpaRepository<Trip, Integer> {
}