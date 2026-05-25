package com.transport.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.transport.backend.dto.order.OrderSimpleResponse;
import com.transport.backend.dto.trip.CreateTripRequest;
import com.transport.backend.dto.trip.TripResponse;
import com.transport.backend.dto.trip.VehicleSuggestionResponse;
import com.transport.backend.entity.Order;
import com.transport.backend.service.TripService;

@RestController
@RequestMapping("/api/trips")
@CrossOrigin("*")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    // =====================================================
    // GET ALL TRIPS
    // =====================================================

    @GetMapping
    public List<TripResponse> getAll() {
        return tripService.getAll();
    }

    // =====================================================
    // GET DETAIL
    // =====================================================

    @GetMapping("/{id}")
    public TripResponse getById(@PathVariable Integer id) {
        return tripService.getById(id);
    }

    // =====================================================
    // GET ORDERS BY ROUTE
    // =====================================================

    @GetMapping("/route/{routeId}/orders")
    public List<OrderSimpleResponse> getOrdersByRoute(
            @PathVariable Integer routeId) {

        return tripService.getPendingOrdersByRoute(routeId);
    }

    // =====================================================
    // SUGGEST VEHICLES
    // =====================================================

    @GetMapping("/route/{routeId}/vehicles")
    public List<VehicleSuggestionResponse> suggestVehicles(
            @PathVariable Integer routeId) {

        return tripService.suggestVehicles(routeId);
    }

    // =====================================================
    // CREATE TRIP
    // =====================================================

    @PostMapping
    public TripResponse createTrip(
            @RequestBody CreateTripRequest request) {

        System.out.println("POST /trips called");

        System.out.println(request.getDepartureTime());

        return tripService.createTrip(request);
    }

    @GetMapping("/driver/{driverId}/current")
    public TripResponse getCurrentTrip(
            @PathVariable Integer driverId) {

        return tripService.getCurrentTripByDriver(
                driverId);
    }
}