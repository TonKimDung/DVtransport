package com.transport.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transport.backend.entity.DriverLicense;

public interface DriverLicenseRepository extends JpaRepository<DriverLicense, Integer> {
    List<DriverLicense> findByDriverId(Integer driverId);
    Optional<DriverLicense> findByLicenseNumber(String licenseNumber);
}