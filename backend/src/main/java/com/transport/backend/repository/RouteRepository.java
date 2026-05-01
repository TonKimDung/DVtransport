package com.transport.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transport.backend.entity.Route;

public interface RouteRepository extends JpaRepository<Route, Integer> {
}