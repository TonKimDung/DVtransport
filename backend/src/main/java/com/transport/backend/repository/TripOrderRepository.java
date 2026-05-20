package com.transport.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transport.backend.entity.TripOrder;

public interface TripOrderRepository
                extends JpaRepository<TripOrder, Integer> {
}