package com.transport.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transport.backend.entity.RoutePoint;

public interface RoutePointRepository extends JpaRepository<RoutePoint, Integer> {
    List<RoutePoint> findByRouteIdOrderBySeqAsc(Integer routeId);
}