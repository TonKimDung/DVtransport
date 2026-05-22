package com.transport.backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.transport.backend.dto.vehicle.VehicleRequest;
import com.transport.backend.entity.Vehicle;
import com.transport.backend.entity.VehicleDriverAssignment;
import com.transport.backend.repository.VehicleRepository;
import com.transport.backend.repository.driver_assignment.VehicleDriverAssignmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleDriverAssignmentRepository assignmentRepo;

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
                .currentLocation(request.getCurrentLocation())
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
        vehicle.setCurrentLocation(request.getCurrentLocation());
        vehicle.setManufactureYear(request.getManufactureYear());
        vehicle.setInspectionExpiry(request.getInspectionExpiry());
        vehicle.setInsuranceExpiry(request.getInsuranceExpiry());

        return vehicleRepository.save(vehicle);
    }

    public void deleteVehicle(Integer id) {
        Vehicle vehicle = getVehicleById(id);
        vehicleRepository.delete(vehicle);
    }

    public List<Vehicle> getAvailableVehicles() {

        // lấy assignment ACTIVE
        List<VehicleDriverAssignment> activeAssignments = assignmentRepo
                .findByStatus(
                        "ACTIVE");

        // lấy id xe đang được gán
        Set<Integer> assignedVehicleIds = activeAssignments
                .stream()
                .map(a -> a.getVehicle()
                        .getId())
                .collect(
                        Collectors.toSet());

        // lọc xe chưa được gán
        return vehicleRepository
                .findAll()
                .stream()
                .filter(v -> !assignedVehicleIds
                        .contains(
                                v.getId()))
                .toList();
    }
}