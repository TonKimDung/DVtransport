package com.transport.backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.transport.backend.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

        boolean existsByOrderCode(String orderCode);

        List<Order> findByStatus(String status);

        List<Order> findByCreatedAtBetween(
                        LocalDateTime start,
                        LocalDateTime end);

        List<Order> findByRoute_IdAndStatus(
                        Integer routeId,
                        String status);

        // 🚛 TÌM ĐƠN PHÙ HỢP TUYẾN ĐƯỜNG
        @Query("""
                            SELECT o
                            FROM Order o
                            JOIN o.route r
                            WHERE
                                LOWER(r.startLocation) LIKE LOWER(CONCAT('%', :startLocation, '%'))
                                AND
                                (
                                    LOWER(r.endLocation) LIKE LOWER(CONCAT('%', :endLocation, '%'))
                                    OR
                                    LOWER(:endLocation) LIKE LOWER(CONCAT('%', r.endLocation, '%'))
                                )
                                AND o.status IN ('CREATED', 'PENDING')
                        """)
        List<Order> findSuitableOrders(
                        String startLocation,
                        String endLocation);

        @Query("""
                            SELECT o
                            FROM Order o
                            WHERE o.route.id = :routeId
                            AND o.status IN ('CREATED', 'PENDING')
                        """)
        List<Order> findAvailableOrdersByRoute(
                        Integer routeId);
}