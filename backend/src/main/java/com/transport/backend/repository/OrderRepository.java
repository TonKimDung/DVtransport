package com.transport.backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transport.backend.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    boolean existsByOrderCode(String orderCode);

    List<Order> findByStatus(String status);

    List<Order> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}