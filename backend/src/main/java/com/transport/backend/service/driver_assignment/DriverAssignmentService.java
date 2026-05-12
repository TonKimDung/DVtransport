package com.transport.backend.service.driver_assignment;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.transport.backend.dto.driver_assignment.AssignDriverRequest;
import com.transport.backend.dto.driver_assignment.AssignmentResponse;
import com.transport.backend.dto.driver_assignment.DriverWorkResponse;
import com.transport.backend.entity.Driver;
import com.transport.backend.entity.DriverWorkLog;
import com.transport.backend.entity.Vehicle;
import com.transport.backend.entity.VehicleDriverAssignment;
import com.transport.backend.repository.DriverRepository;
import com.transport.backend.repository.VehicleRepository;
import com.transport.backend.repository.driver_assignment.*;
import com.transport.backend.repository.driver_assignment.VehicleDriverAssignmentRepository;

@Service
public class DriverAssignmentService {

    private final VehicleDriverAssignmentRepository assignmentRepo;
    private final DriverRepository driverRepo;
    private final VehicleRepository vehicleRepo;
    private final DriverWorkLogRepository workLogRepo;

    public DriverAssignmentService(
            VehicleDriverAssignmentRepository assignmentRepo,
            DriverRepository driverRepo,
            VehicleRepository vehicleRepo,
            DriverWorkLogRepository workLogRepo) {

        this.assignmentRepo = assignmentRepo;
        this.driverRepo = driverRepo;
        this.vehicleRepo = vehicleRepo;
        this.workLogRepo = workLogRepo;
    }

    // 🚗 1. GÁN TÀI XẾ
    public AssignmentResponse assign(AssignDriverRequest req) {

        Vehicle vehicle = vehicleRepo.findById(req.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        Driver driver = driverRepo.findById(req.getDriverId())
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        VehicleDriverAssignment entity = new VehicleDriverAssignment();
        entity.setVehicle(vehicle);
        entity.setDriver(driver);
        entity.setAssignedDate(LocalDate.now());

        return map(assignmentRepo.save(entity));
    }

    // 📄 GET ALL ASSIGNMENTS
    public List<AssignmentResponse> getAll() {
        return assignmentRepo.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    // ⏱️ 2. THEO DÕI GIỜ LÀM
    public DriverWorkResponse getWorkToday(Integer driverId) {

        List<DriverWorkLog> logs = workLogRepo.findByDriver_IdAndWorkDate(driverId, LocalDate.now());

        double totalHours = logs.stream()
                .mapToDouble(l -> l.getDrivingHours() == null ? 0 : l.getDrivingHours().doubleValue())
                .sum();

        DriverWorkResponse res = new DriverWorkResponse();
        res.setDriverId(driverId);
        res.setTotalHours(totalHours);

        // 🚨 3. CẢNH BÁO QUÁ GIỜ (ví dụ > 10h)
        res.setOverworked(totalHours > 10);

        return res;
    }

    // 🔁 MAPPER
    private AssignmentResponse map(VehicleDriverAssignment e) {

        AssignmentResponse res = new AssignmentResponse();

        res.setId(e.getId());

        if (e.getVehicle() != null) {
            res.setVehicleId(e.getVehicle().getId());
            res.setPlateNumber(e.getVehicle().getPlateNumber());
        }

        if (e.getDriver() != null) {
            res.setDriverId(e.getDriver().getId());
            res.setDriverName(e.getDriver().getFullName());
        }

        res.setAssignedDate(e.getAssignedDate());

        return res;
    }
}