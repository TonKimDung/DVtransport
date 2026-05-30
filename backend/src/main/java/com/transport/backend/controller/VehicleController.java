package com.transport.backend.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transport.backend.dto.vehicle.VehicleRequest;
import com.transport.backend.entity.Vehicle;
import com.transport.backend.service.VehicleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
@CrossOrigin("*")
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DIEU_PHOI_VIEN')")
    public List<Vehicle> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIEU_PHOI_VIEN')")
    public Vehicle getVehicleById(@PathVariable Integer id) {
        return vehicleService.getVehicleById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DIEU_PHOI_VIEN')")
    public Vehicle createVehicle(@RequestBody VehicleRequest request) {
        return vehicleService.createVehicle(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIEU_PHOI_VIEN')")
    public Vehicle updateVehicle(
            @PathVariable Integer id,
            @RequestBody VehicleRequest request) {
        return vehicleService.updateVehicle(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteVehicle(@PathVariable Integer id) {
        vehicleService.deleteVehicle(id);
        return "Xóa phương tiện thành công";
    }

    @GetMapping("/available")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIEU_PHOI_VIEN')")
    public List<Vehicle> getAvailableVehicles() {
        return vehicleService.getAvailableVehicles();
    }
}