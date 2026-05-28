package com.transport.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transport.backend.dto.driver_license.CreateDriverLicenseRequest;
import com.transport.backend.dto.driver_license.DriverLicenseDTO;
import com.transport.backend.dto.driver_license.UpdateDriverLicenseRequest;
import com.transport.backend.entity.Driver;
import com.transport.backend.entity.DriverLicense;
import com.transport.backend.entity.LicenseType;
import com.transport.backend.mapper.DriverLicenseMapper;
import com.transport.backend.repository.DriverLicenseRepository;
import com.transport.backend.repository.DriverRepository;
import com.transport.backend.repository.LicenseTypeRepository;

@Service
public class DriverLicenseService {

    @Autowired
    private DriverLicenseRepository repository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private LicenseTypeRepository licenseTypeRepository;

    // =========================
    // CREATE
    // =========================

    public DriverLicenseDTO create(
            CreateDriverLicenseRequest req) {

        if (repository.existsByLicenseNumber(
                req.getLicenseNumber())) {

            throw new RuntimeException(
                    "License number already exists");
        }

        Driver driver = driverRepository.findById(
                req.getDriverId())
                .orElseThrow(() -> new RuntimeException(
                        "Driver not found"));

        // FE truyền licenseClass
        LicenseType licenseType = licenseTypeRepository
                .findByLicenseClass(
                        req.getLicenseClass())
                .orElseThrow(() -> new RuntimeException(
                        "License class not found"));

        DriverLicense d = new DriverLicense();

        d.setDriver(driver);

        d.setLicenseType(
                licenseType);

        d.setLicenseNumber(
                req.getLicenseNumber());

        d.setIssueDate(
                req.getIssueDate());

        d.setExpiryDate(
                req.getExpiryDate());

        d.setStatus(
                req.getStatus());

        repository.save(d);

        return DriverLicenseMapper.toDTO(d);
    }

    // =========================
    // GET ALL
    // =========================

    public List<DriverLicenseDTO> getAll() {

        return repository.findAll()
                .stream()
                .map(
                        DriverLicenseMapper::toDTO)
                .toList();
    }

    // =========================
    // GET BY ID
    // =========================

    public DriverLicenseDTO getById(
            Integer id) {

        DriverLicense d = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "License not found"));

        return DriverLicenseMapper.toDTO(d);
    }

    // =========================
    // GET BY DRIVER
    // =========================

    public List<DriverLicenseDTO> getByDriver(
            Integer driverId) {

        return repository.findByDriverId(
                driverId)
                .stream()
                .map(
                        DriverLicenseMapper::toDTO)
                .toList();
    }

    // =========================
    // UPDATE
    // =========================

    public DriverLicenseDTO update(
            Integer id,
            UpdateDriverLicenseRequest req) {

        DriverLicense d = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "License not found"));

        // FE truyền licenseClass
        LicenseType licenseType = licenseTypeRepository
                .findByLicenseClass(
                        req.getLicenseClass())
                .orElseThrow(() -> new RuntimeException(
                        "License class not found"));

        d.setLicenseType(
                licenseType);

        d.setIssueDate(
                req.getIssueDate());

        d.setExpiryDate(
                req.getExpiryDate());

        d.setStatus(
                req.getStatus());

        repository.save(d);

        return DriverLicenseMapper.toDTO(d);
    }

    // =========================
    // DELETE
    // =========================

    public void delete(
            Integer id) {

        if (!repository.existsById(id)) {

            throw new RuntimeException(
                    "License not found");
        }

        repository.deleteById(id);
    }
}