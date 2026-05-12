package com.transport.backend.service;

import com.transport.backend.dto.route.RouteRequest;
import com.transport.backend.dto.route.RouteResponse;
import com.transport.backend.entity.Route;
import com.transport.backend.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final RouteRepository routeRepository;

    private RouteResponse toResponse(Route route) {
        return RouteResponse.builder()
                .id(route.getId())
                .routeName(route.getRouteName())
                .startLocation(route.getStartLocation())
                .endLocation(route.getEndLocation())
                .distanceKm(route.getDistanceKm())
                .estimatedHours(route.getEstimatedHours())
                .build();
    }

    public List<RouteResponse> getAllRoutes() {
        return routeRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public RouteResponse getRouteById(Integer id) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tuyến đường"));
        return toResponse(route);
    }

    public RouteResponse createRoute(RouteRequest request) {
        if (routeRepository.existsByRouteName(request.getRouteName())) {
            throw new RuntimeException("Tên tuyến đường đã tồn tại");
        }

        Route route = Route.builder()
                .routeName(request.getRouteName())
                .startLocation(request.getStartLocation())
                .endLocation(request.getEndLocation())
                .distanceKm(request.getDistanceKm())
                .estimatedHours(request.getEstimatedHours())
                .build();

        return toResponse(routeRepository.save(route));
    }

    public RouteResponse updateRoute(Integer id, RouteRequest request) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tuyến đường"));

        route.setRouteName(request.getRouteName());
        route.setStartLocation(request.getStartLocation());
        route.setEndLocation(request.getEndLocation());
        route.setDistanceKm(request.getDistanceKm());
        route.setEstimatedHours(request.getEstimatedHours());

        return toResponse(routeRepository.save(route));
    }

    public void deleteRoute(Integer id) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tuyến đường"));
        routeRepository.delete(route);
    }
}