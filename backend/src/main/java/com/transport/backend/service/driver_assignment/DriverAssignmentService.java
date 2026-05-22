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
import com.transport.backend.repository.driver_assignment.DriverWorkLogRepository;
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

    // ==========================
    // 1. GÁN TÀI XẾ
    // ==========================
    public AssignmentResponse assign(
            AssignDriverRequest req) {

        Driver driver = driverRepo.findById(
                req.getDriverId())
                .orElseThrow(() -> new RuntimeException(
                        "Không tìm thấy tài xế"));

        Vehicle vehicle = vehicleRepo.findById(
                req.getVehicleId())
                .orElseThrow(() -> new RuntimeException(
                        "Không tìm thấy phương tiện"));

        VehicleDriverAssignment assignment = new VehicleDriverAssignment();

        assignment.setDriver(driver);
        assignment.setVehicle(vehicle);
        assignment.setAssignedDate(
                req.getAssignedDate());

        assignment.setStatus(
                "ACTIVE");

        assignmentRepo.save(
                assignment);

        return map(
                assignment);
    }

    // ==========================
    // 2. GET ALL ASSIGNMENTS
    // ==========================
    public List<AssignmentResponse> getAll() {

        return assignmentRepo.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    // ==========================
    // 3. THEO DÕI GIỜ LÀM
    // ==========================
    public DriverWorkResponse getWorkToday(
            Integer driverId) {

        List<DriverWorkLog> logs = workLogRepo.findByDriver_IdAndWorkDate(
                driverId,
                LocalDate.now());

        double totalHours = logs.stream()
                .mapToDouble(log -> log.getDrivingHours() == null
                        ? 0
                        : log.getDrivingHours()
                                .doubleValue())
                .sum();

        DriverWorkResponse res = new DriverWorkResponse();

        res.setDriverId(driverId);
        res.setTotalHours(totalHours);

        // quá 10 giờ
        res.setOverworked(
                totalHours > 10);

        return res;
    }

    // ==========================
    // MAPPER
    // ==========================
    private AssignmentResponse map(
            VehicleDriverAssignment e) {

        AssignmentResponse res = new AssignmentResponse();

        res.setId(e.getId());

        if (e.getVehicle() != null) {
            res.setVehicleId(
                    e.getVehicle().getId());

            res.setPlateNumber(
                    e.getVehicle()
                            .getPlateNumber());
        }

        if (e.getDriver() != null) {
            res.setDriverId(
                    e.getDriver().getId());

            res.setDriverName(
                    e.getDriver()
                            .getFullName());
        }

        res.setAssignedDate(
                e.getAssignedDate());

        res.setStatus(
                e.getStatus());

        return res;
    }

    public AssignmentResponse deactivate(
            Integer id) {

        VehicleDriverAssignment assignment = assignmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Không tìm thấy phân công"));

        assignment.setStatus("INACTIVE");

        assignmentRepo.save(assignment);

        return map(assignment);
    }
}