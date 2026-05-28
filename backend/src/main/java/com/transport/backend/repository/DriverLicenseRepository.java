package com.transport.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transport.backend.entity.DriverLicense;

public interface DriverLicenseRepository
                extends JpaRepository<DriverLicense, Integer> {

        boolean existsByLicenseNumber(
                        String licenseNumber);

        List<DriverLicense> findByDriverId(
                        Integer driverId);
}