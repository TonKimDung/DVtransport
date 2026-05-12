package com.transport.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.transport.backend.dto.trip.*;
import com.transport.backend.entity.Trip;
import com.transport.backend.service.TripService;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    private final TripService service;

    public TripController(TripService service) {
        this.service = service;
    }

    // 🚚 CREATE
    @PostMapping
    public TripResponse create(@RequestParam Integer vehicleId) {
        return service.create(vehicleId);
    }

    // 👨‍✈️ ASSIGN DRIVER
    @PutMapping("/{id}/assign-driver")
    public TripResponse assign(@PathVariable Integer id,
            @RequestBody AssignTripRequest req) {
        return service.assignDriver(id, req);
    }

    // 📄 GET ALL
    @GetMapping
    public List<TripResponse> getAll() {
        return service.getAll();
    }

    // 📄 GET DETAIL
    @GetMapping("/{id}")
    public TripResponse getById(@PathVariable Integer id) {
        return service.getById(id);
    }
}