package com.transport.backend.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transport.backend.dto.order.OrderSimpleResponse;
import com.transport.backend.dto.trip.CreateTripRequest;
import com.transport.backend.dto.trip.TripResponse;
import com.transport.backend.dto.trip.VehicleSuggestionResponse;
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
    @PreAuthorize("hasAnyRole('ADMIN', 'DIEU_PHOI_VIEN')")
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
    @PreAuthorize("hasAnyRole('ADMIN', 'DIEU_PHOI_VIEN')")
    public List<VehicleSuggestionResponse> suggestVehicles(
            @PathVariable Integer routeId) {

        return tripService.suggestVehicles(routeId);
    }

    // =====================================================
    // CREATE TRIP
    // =====================================================

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DIEU_PHOI_VIEN')")
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

    @PatchMapping("/{id}/complete")
        public String completeTrip(@PathVariable Integer id) {
            tripService.completeTrip(id);
            return "Hoàn thành chuyến đi, cập nhật đơn hàng và ghi work log thành công";
    }

    @GetMapping("/driver/{driverId}")
    public List<TripResponse> getTripsByDriver(@PathVariable Integer driverId) {
        return tripService.getTripsByDriver(driverId);
    }
}