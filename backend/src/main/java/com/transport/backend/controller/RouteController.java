package com.transport.backend.controller;

import com.transport.backend.dto.route.RouteRequest;
import com.transport.backend.dto.route.RouteResponse;
import com.transport.backend.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
@CrossOrigin("*")
public class RouteController {

    private final RouteService routeService;

    @GetMapping
    public List<RouteResponse> getAllRoutes() {
        return routeService.getAllRoutes();
    }

    @GetMapping("/{id}")
    public RouteResponse getRouteById(@PathVariable Integer id) {
        return routeService.getRouteById(id);
    }

    @PostMapping
    public RouteResponse createRoute(@RequestBody RouteRequest request) {
        return routeService.createRoute(request);
    }

    @PutMapping("/{id}")
    public RouteResponse updateRoute(
            @PathVariable Integer id,
            @RequestBody RouteRequest request
    ) {
        return routeService.updateRoute(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteRoute(@PathVariable Integer id) {
        routeService.deleteRoute(id);
        return "Xóa tuyến đường thành công";
    }
}