package com.transport.backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transport.backend.entity.TripExpense;

public interface TripExpenseRepository extends JpaRepository<TripExpense, Integer> {
    List<TripExpense> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    List<TripExpense> findByTripId(Integer tripId);
}