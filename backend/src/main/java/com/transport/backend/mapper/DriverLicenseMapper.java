package com.transport.backend.mapper;

import com.transport.backend.dto.driver_license.DriverLicenseDTO;
import com.transport.backend.entity.DriverLicense;

public class DriverLicenseMapper {

    public static DriverLicenseDTO toDTO(
            DriverLicense d) {

        DriverLicenseDTO dto = new DriverLicenseDTO();

        dto.setId(
                d.getId());

        // =========================
        // DRIVER
        // =========================

        dto.setDriverId(
                d.getDriver()
                        .getId());

        dto.setDriverName(
                d.getDriver()
                        .getFullName());

        // =========================
        // LICENSE
        // =========================

        dto.setLicenseNumber(
                d.getLicenseNumber());

        // =========================
        // LICENSE TYPE
        // =========================

        dto.setLicenseTypeId(
                d.getLicenseType()
                        .getId());

        dto.setLicenseClass(
                d.getLicenseType()
                        .getLicenseClass());

        dto.setBaseSalary(
                d.getLicenseType()
                        .getBaseSalary());

        // =========================
        // DATE
        // =========================

        dto.setIssueDate(
                d.getIssueDate());

        dto.setExpiryDate(
                d.getExpiryDate());

        // =========================
        // STATUS
        // =========================

        dto.setStatus(
                d.getStatus());

        dto.setCreatedAt(
                d.getCreatedAt());

        return dto;
    }
}