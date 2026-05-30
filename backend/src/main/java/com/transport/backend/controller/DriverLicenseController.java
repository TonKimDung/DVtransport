package com.transport.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.transport.backend.dto.driver_license.CreateDriverLicenseRequest;
import com.transport.backend.dto.driver_license.DriverLicenseDTO;
import com.transport.backend.dto.driver_license.UpdateDriverLicenseRequest;
import com.transport.backend.service.DriverLicenseService;

@RestController
@RequestMapping("/api/driver-licenses")
@CrossOrigin("*")
public class DriverLicenseController {

    @Autowired
    private DriverLicenseService service;

    @PostMapping
    public DriverLicenseDTO create(
            @RequestBody CreateDriverLicenseRequest req) {

        return service.create(req);
    }

    @GetMapping
    public List<DriverLicenseDTO> getAll() {

        return service.getAll();
    }

    @GetMapping("/{id}")
    public DriverLicenseDTO getById(
            @PathVariable Integer id) {

        return service.getById(id);
    }

    @GetMapping("/driver/{driverId}")
    public List<DriverLicenseDTO> getByDriver(
            @PathVariable Integer driverId) {

        return service.getByDriver(driverId);
    }

    @PutMapping("/{id}")
    public DriverLicenseDTO update(
            @PathVariable Integer id,

            @RequestBody UpdateDriverLicenseRequest req) {

        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Integer id) {

        service.delete(id);
    }
}