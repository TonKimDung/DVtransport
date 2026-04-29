package com.transport.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.transport.backend.entity.Driver;

public interface DriverRepository extends JpaRepository<Driver, Integer> {
    boolean existsByLicenseNumber(String licenseNumber);
}