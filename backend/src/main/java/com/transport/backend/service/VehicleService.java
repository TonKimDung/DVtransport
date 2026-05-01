package com.transport.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.transport.backend.dto.vehicle.VehicleRequest;
import com.transport.backend.entity.Vehicle;
import com.transport.backend.repository.VehicleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle getVehicleById(Integer id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phương tiện"));
    }

    public Vehicle createVehicle(VehicleRequest request) {
        if (vehicleRepository.findByPlateNumber(request.getPlateNumber()).isPresent()) {
            throw new RuntimeException("Biển số xe đã tồn tại");
        }

        Vehicle vehicle = Vehicle.builder()
                .plateNumber(request.getPlateNumber())
                .vehicleType(request.getVehicleType())
                .capacity(request.getCapacity())
                .status(request.getStatus())
                .manufactureYear(request.getManufactureYear())
                .inspectionExpiry(request.getInspectionExpiry())
                .insuranceExpiry(request.getInsuranceExpiry())
                .createdAt(LocalDateTime.now())
                .build();

        return vehicleRepository.save(vehicle);
    }

    public Vehicle updateVehicle(Integer id, VehicleRequest request) {
        Vehicle vehicle = getVehicleById(id);

        vehicle.setPlateNumber(request.getPlateNumber());
        vehicle.setVehicleType(request.getVehicleType());
        vehicle.setCapacity(request.getCapacity());
        vehicle.setStatus(request.getStatus());
        vehicle.setManufactureYear(request.getManufactureYear());
        vehicle.setInspectionExpiry(request.getInspectionExpiry());
        vehicle.setInsuranceExpiry(request.getInsuranceExpiry());

        return vehicleRepository.save(vehicle);
    }

    public void deleteVehicle(Integer id) {
        Vehicle vehicle = getVehicleById(id);
        vehicleRepository.delete(vehicle);
    }
}