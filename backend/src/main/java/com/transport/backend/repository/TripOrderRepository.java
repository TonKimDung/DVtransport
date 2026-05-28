package com.transport.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transport.backend.entity.TripOrder;

public interface TripOrderRepository extends JpaRepository<TripOrder, Integer> {
    List<TripOrder> findByTripId(Integer tripId);
}