package com.transport.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transport.backend.entity.LicenseType;

public interface LicenseTypeRepository
        extends JpaRepository<LicenseType, Integer> {

    Optional<LicenseType> findByLicenseClass(String licenseClass);

    boolean existsByLicenseClass(String licenseClass);
}