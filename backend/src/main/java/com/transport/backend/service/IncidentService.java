package com.transport.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.transport.backend.dto.incident.IncidentRequest;
import com.transport.backend.dto.incident.IncidentResponse;
import com.transport.backend.entity.Driver;
import com.transport.backend.entity.Incident;
import com.transport.backend.entity.Trip;
import com.transport.backend.entity.Vehicle;
import com.transport.backend.repository.DriverRepository;
import com.transport.backend.repository.IncidentRepository;
import com.transport.backend.repository.TripRepository;
import com.transport.backend.repository.VehicleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IncidentService {

    private final IncidentRepository incidentRepository;
    private final TripRepository tripRepository;
    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;

    private IncidentResponse toResponse(Incident incident) {
        return IncidentResponse.builder()
                .id(incident.getId())
                .tripId(incident.getTrip() != null ? incident.getTrip().getId() : null)
                .tripCode(incident.getTrip() != null ? incident.getTrip().getTripCode() : null)
                .vehicleId(incident.getVehicle() != null ? incident.getVehicle().getId() : null)
                .plateNumber(incident.getVehicle() != null ? incident.getVehicle().getPlateNumber() : null)
                .driverId(incident.getDriver() != null ? incident.getDriver().getId() : null)
                .driverName(incident.getDriver() != null ? incident.getDriver().getFullName() : null)
                .incidentType(incident.getIncidentType())
                .description(incident.getDescription())
                .incidentTime(incident.getIncidentTime())
                .status(incident.getStatus())
                .createdAt(incident.getCreatedAt())
                .build();
    }

    public List<IncidentResponse> getAllIncidents() {
        return incidentRepository.findAll().stream().map(this::toResponse).toList();
    }

    public IncidentResponse getIncidentById(Integer id) {
        return toResponse(findIncidentEntityById(id));
    }

    private Incident findIncidentEntityById(Integer id) {
        return incidentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sự cố"));
    }

    public IncidentResponse createIncident(IncidentRequest request) {
        Trip trip = request.getTripId() != null
                ? tripRepository.findById(request.getTripId()).orElseThrow(() -> new RuntimeException("Không tìm thấy chuyến đi"))
                : null;

        Vehicle vehicle = request.getVehicleId() != null
                ? vehicleRepository.findById(request.getVehicleId()).orElseThrow(() -> new RuntimeException("Không tìm thấy phương tiện"))
                : null;

        Driver driver = request.getDriverId() != null
                ? driverRepository.findById(request.getDriverId()).orElseThrow(() -> new RuntimeException("Không tìm thấy tài xế"))
                : null;

        Incident incident = Incident.builder()
                .trip(trip)
                .vehicle(vehicle)
                .driver(driver)
                .incidentType(request.getIncidentType())
                .description(request.getDescription())
                .incidentTime(request.getIncidentTime() != null ? request.getIncidentTime() : LocalDateTime.now())
                .status(request.getStatus() != null ? request.getStatus() : "Đang xử lý")
                .build();

        return toResponse(incidentRepository.save(incident));
    }

    public IncidentResponse updateIncident(Integer id, IncidentRequest request) {
        Incident incident = findIncidentEntityById(id);

        Trip trip = request.getTripId() != null
                ? tripRepository.findById(request.getTripId()).orElseThrow(() -> new RuntimeException("Không tìm thấy chuyến đi"))
                : null;

        Vehicle vehicle = request.getVehicleId() != null
                ? vehicleRepository.findById(request.getVehicleId()).orElseThrow(() -> new RuntimeException("Không tìm thấy phương tiện"))
                : null;

        Driver driver = request.getDriverId() != null
                ? driverRepository.findById(request.getDriverId()).orElseThrow(() -> new RuntimeException("Không tìm thấy tài xế"))
                : null;

        incident.setTrip(trip);
        incident.setVehicle(vehicle);
        incident.setDriver(driver);
        incident.setIncidentType(request.getIncidentType());
        incident.setDescription(request.getDescription());
        incident.setIncidentTime(request.getIncidentTime());
        incident.setStatus(request.getStatus());

        return toResponse(incidentRepository.save(incident));
    }

    public List<IncidentResponse> getIncidentsByStatus(String status) {
        return incidentRepository.findByStatus(status).stream().map(this::toResponse).toList();
    }

    public IncidentResponse updateIncidentStatus(Integer id, String status) {
        Incident incident = findIncidentEntityById(id);
        incident.setStatus(status);
        return toResponse(incidentRepository.save(incident));
    }

    public void deleteIncident(Integer id) {
        incidentRepository.delete(findIncidentEntityById(id));
    }
}