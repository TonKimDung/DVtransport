package com.transport.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.transport.backend.dto.trip.*;
import com.transport.backend.entity.*;
import com.transport.backend.repository.*;
import com.transport.backend.repository.driver_assignment.VehicleDriverAssignmentRepository;
import com.transport.backend.repository.trip.TripRepository;

@Service
public class TripService {

    private final TripRepository tripRepo;
    private final VehicleRepository vehicleRepo;
    private final DriverRepository driverRepo;
    private final VehicleDriverAssignmentRepository assignmentRepo;

    public TripService(
            TripRepository tripRepo,
            VehicleRepository vehicleRepo,
            VehicleDriverAssignmentRepository assignmentRepo,
            DriverRepository driverRepo) {

        this.tripRepo = tripRepo;
        this.vehicleRepo = vehicleRepo;
        this.assignmentRepo = assignmentRepo;
        this.driverRepo = driverRepo;
    }

    // 🚚 CREATE TRIP
    // 🚛 CREATE TRIP (FIX LOGIC)
    // 🚛 CREATE TRIP (FIX LOGIC)
    public TripResponse create(Integer vehicleId) {

        Vehicle vehicle = vehicleRepo.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        VehicleDriverAssignment assignment = assignmentRepo
                .findTopByVehicle_IdOrderByAssignedDateDesc(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle chưa được gán tài xế"));

        Driver driver = assignment.getDriver();

        if (driver == null) {
            throw new RuntimeException("Xe chưa có tài xế");
        }

        Trip trip = new Trip();
        trip.setVehicle(vehicle);
        trip.setDriver(driver);
        trip.setDepartureTime(LocalDateTime.now());
        trip.setStatus("CREATED");
        trip.setTripCode("TRIP-" + System.currentTimeMillis());

        Trip saved = tripRepo.save(trip);

        return map(saved); // 🔥 QUAN TRỌNG
    }

    // 👨‍✈️ ASSIGN DRIVER
    public TripResponse assignDriver(Integer tripId, AssignTripRequest req) {

        Trip t = tripRepo.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        Driver d = driverRepo.findById(req.getDriverId())
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        t.setDriver(d);
        t.setStatus("ASSIGNED");

        return map(tripRepo.save(t));
    }

    // 📄 GET ALL
    public List<TripResponse> getAll() {
        return tripRepo.findAll().stream().map(this::map).toList();
    }

    // 📄 GET DETAIL
    public TripResponse getById(Integer id) {
        return map(tripRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Trip not found")));
    }

    // 🔁 MAPPER
    private TripResponse map(Trip t) {

        TripResponse res = new TripResponse();

        res.setId(t.getId());
        res.setTripCode(t.getTripCode());

        if (t.getVehicle() != null) {
            res.setVehicleId(t.getVehicle().getId());
            res.setPlateNumber(t.getVehicle().getPlateNumber());
        }

        if (t.getDriver() != null) {
            res.setDriverId(t.getDriver().getId());
            res.setDriverName(t.getDriver().getFullName());
        }

        res.setDepartureTime(t.getDepartureTime());
        res.setArrivalTime(t.getArrivalTime());
        res.setStatus(t.getStatus());

        return res;
    }
}