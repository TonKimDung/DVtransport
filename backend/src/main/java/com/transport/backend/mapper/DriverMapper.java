package com.transport.backend.mapper;

import com.transport.backend.entity.Driver;
import com.transport.backend.dto.driver.DriverDTO;

public class DriverMapper {

    public static DriverDTO toDTO(Driver d) {
        DriverDTO dto = new DriverDTO();

        dto.setId(d.getId());
        dto.setFullName(d.getFullName());
        dto.setPhone(d.getPhone());
        dto.setEmail(d.getEmail());
        dto.setAddress(d.getAddress());
        dto.setLicenseNumber(d.getLicenseNumber());
        dto.setLicenseExpiry(d.getLicenseExpiry());
        dto.setStatus(d.getStatus());
        dto.setCreatedAt(d.getCreatedAt());

        return dto;
    }
}