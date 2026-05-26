package com.transport.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transport.backend.dto.driver_license.DriverLicenseRequest;
import com.transport.backend.dto.driver_license.DriverLicenseResponse;
import com.transport.backend.service.DriverLicenseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/driver-licenses")
@RequiredArgsConstructor
@CrossOrigin("*")
public class DriverLicenseController {

    private final DriverLicenseService driverLicenseService;

    @GetMapping
    public List<DriverLicenseResponse> getAllLicenses() {
        return driverLicenseService.getAllLicenses();
    }

    @GetMapping("/{id}")
    public DriverLicenseResponse getLicenseById(@PathVariable Integer id) {
        return driverLicenseService.getLicenseById(id);
    }

    @GetMapping("/driver/{driverId}")
    public List<DriverLicenseResponse> getLicensesByDriver(@PathVariable Integer driverId) {
        return driverLicenseService.getLicensesByDriver(driverId);
    }

    @PostMapping
    public DriverLicenseResponse createLicense(@RequestBody DriverLicenseRequest request) {
        return driverLicenseService.createLicense(request);
    }

    @PutMapping("/{id}")
    public DriverLicenseResponse updateLicense(
            @PathVariable Integer id,
            @RequestBody DriverLicenseRequest request
    ) {
        return driverLicenseService.updateLicense(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteLicense(@PathVariable Integer id) {
        driverLicenseService.deleteLicense(id);
        return "Xóa bằng lái thành công";
    }
}