package com.transport.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transport.backend.entity.Driver;
import com.transport.backend.repository.DriverRepository;
import com.transport.backend.dto.driver.CreateDriverRequest;
import com.transport.backend.dto.driver.UpdateDriverRequest;
import com.transport.backend.dto.driver.DriverDTO;
import com.transport.backend.mapper.DriverMapper;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    public DriverDTO create(CreateDriverRequest req) {

        if (driverRepository.existsByLicenseNumber(req.getLicenseNumber())) {
            throw new RuntimeException("License already exists");
        }

        Driver d = new Driver();
        d.setFullName(req.getFullName());
        d.setPhone(req.getPhone());
        d.setEmail(req.getEmail());
        d.setAddress(req.getAddress());
        d.setLicenseNumber(req.getLicenseNumber());
        d.setLicenseExpiry(req.getLicenseExpiry());
        d.setStatus("ACTIVE");

        driverRepository.save(d);

        return DriverMapper.toDTO(d);
    }

    public java.util.List<DriverDTO> getAll() {
        java.util.List<Driver> drivers = driverRepository.findAll();
        java.util.List<DriverDTO> dtos = new java.util.ArrayList<>();

        for (Driver d : drivers) {
            dtos.add(DriverMapper.toDTO(d));
        }

        return dtos;
    }

    public DriverDTO getById(Integer id) {
        Driver d = driverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        return DriverMapper.toDTO(d);
    }

    public DriverDTO update(Integer id, UpdateDriverRequest req) {

        Driver d = driverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        d.setFullName(req.getFullName());
        d.setPhone(req.getPhone());
        d.setEmail(req.getEmail());
        d.setAddress(req.getAddress());
        d.setLicenseExpiry(req.getLicenseExpiry());
        d.setStatus(req.getStatus());

        driverRepository.save(d);

        return DriverMapper.toDTO(d);
    }

    public void delete(Integer id) {

        if (!driverRepository.existsById(id)) {
            throw new RuntimeException("Driver not found");
        }

        driverRepository.deleteById(id);
    }
}