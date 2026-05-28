package com.transport.backend.mapper;

import com.transport.backend.dto.license_type.LicenseTypeDTO;
import com.transport.backend.entity.LicenseType;

public class LicenseTypeMapper {

    public static LicenseTypeDTO toDTO(
            LicenseType l) {

        LicenseTypeDTO dto = new LicenseTypeDTO();

        dto.setId(
                l.getId());

        dto.setLicenseClass(
                l.getLicenseClass());

        dto.setBaseSalary(
                l.getBaseSalary());

        dto.setDescription(
                l.getDescription());

        return dto;
    }
}