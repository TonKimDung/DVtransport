package com.transport.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.transport.backend.dto.driver_license.DriverLicenseRequest;
import com.transport.backend.dto.driver_license.DriverLicenseResponse;
import com.transport.backend.entity.Driver;
import com.transport.backend.entity.DriverLicense;
import com.transport.backend.repository.DriverLicenseRepository;
import com.transport.backend.repository.DriverRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DriverLicenseService {

    private final DriverLicenseRepository driverLicenseRepository;
    private final DriverRepository driverRepository;

    private DriverLicenseResponse toResponse(DriverLicense license) {
        return DriverLicenseResponse.builder()
                .id(license.getId())
                .driverId(license.getDriver() != null ? license.getDriver().getId() : null)
                .driverName(license.getDriver() != null ? license.getDriver().getFullName() : null)
                .licenseNumber(license.getLicenseNumber())
                .licenseClass(license.getLicenseClass())
                .issueDate(license.getIssueDate())
                .expiryDate(license.getExpiryDate())
                .fileUrl(license.getFileUrl())
                .status(license.getStatus())
                .createdAt(license.getCreatedAt())
                .build();
    }

    public List<DriverLicenseResponse> getAllLicenses() {
        return driverLicenseRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public DriverLicenseResponse getLicenseById(Integer id) {
        DriverLicense license = driverLicenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bằng lái"));
        return toResponse(license);
    }

    public List<DriverLicenseResponse> getLicensesByDriver(Integer driverId) {
        return driverLicenseRepository.findByDriverId(driverId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public DriverLicenseResponse createLicense(DriverLicenseRequest request) {
        if (driverLicenseRepository.findByLicenseNumber(request.getLicenseNumber()).isPresent()) {
            throw new RuntimeException("Số bằng lái đã tồn tại");
        }

        Driver driver = driverRepository.findById(request.getDriverId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài xế"));

        DriverLicense license = DriverLicense.builder()
                .driver(driver)
                .licenseNumber(request.getLicenseNumber())
                .licenseClass(request.getLicenseClass())
                .issueDate(request.getIssueDate())
                .expiryDate(request.getExpiryDate())
                .fileUrl(request.getFileUrl())
                .status(request.getStatus() != null ? request.getStatus() : "Còn hạn")
                .build();

        return toResponse(driverLicenseRepository.save(license));
    }

    public DriverLicenseResponse updateLicense(Integer id, DriverLicenseRequest request) {
        DriverLicense license = driverLicenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bằng lái"));

        Driver driver = driverRepository.findById(request.getDriverId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài xế"));

        license.setDriver(driver);
        license.setLicenseNumber(request.getLicenseNumber());
        license.setLicenseClass(request.getLicenseClass());
        license.setIssueDate(request.getIssueDate());
        license.setExpiryDate(request.getExpiryDate());
        license.setFileUrl(request.getFileUrl());
        license.setStatus(request.getStatus());

        return toResponse(driverLicenseRepository.save(license));
    }

    public void deleteLicense(Integer id) {
        DriverLicense license = driverLicenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bằng lái"));
        driverLicenseRepository.delete(license);
    }
}