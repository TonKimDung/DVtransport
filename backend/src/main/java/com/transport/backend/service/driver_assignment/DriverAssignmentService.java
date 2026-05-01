// package com.transport.backend.service.driver_assignment;

// import java.time.LocalDate;
// import java.util.List;

// import org.springframework.stereotype.Service;

// import com.transport.backend.dto.driver_assignment.DriverWorkResponse;
// import com.transport.backend.entity.Driver;
// import com.transport.backend.entity.DriverWorkLog;
// import com.transport.backend.entity.Vehicle;
// import com.transport.backend.entity.VehicleDriverAssignment;
// import com.transport.backend.repository.DriverRepository;

// import
// com.transport.backend.repository.driver_assignment.VehicleDriverAssignmentRepository;

// @Service
// public class DriverAssignmentService {

// private final VehicleDriverAssignmentRepository assignmentRepo;
// private final DriverRepository driverRepo;
// private final VehicleRepository vehicleRepo;

// public DriverAssignmentService(
// VehicleDriverAssignmentRepository assignmentRepo,
// DriverRepository driverRepo,
// VehicleRepository vehicleRepo) {

// this.assignmentRepo = assignmentRepo;
// this.driverRepo = driverRepo;
// this.vehicleRepo = vehicleRepo;
// }

// // 🚗 ASSIGN DRIVER
// public AssignmentResponse assign(AssignDriverRequest req) {

// // ✅ LOAD từ DB (KHÔNG dùng new)
// Vehicle vehicle = vehicleRepo.findById(req.getVehicleId())
// .orElseThrow(() -> new RuntimeException("Vehicle not found"));

// Driver driver = driverRepo.findById(req.getDriverId())
// .orElseThrow(() -> new RuntimeException("Driver not found"));

// VehicleDriverAssignment entity = new VehicleDriverAssignment();
// entity.setVehicle(vehicle);
// entity.setDriver(driver);
// entity.setAssignedDate(LocalDate.now());

// VehicleDriverAssignment saved = assignmentRepo.save(entity);

// // ✅ MAP sang DTO (TRÁNH LAZY ERROR)
// return map(saved);
// }

// // 📄 GET ALL
// public List<AssignmentResponse> getAll() {
// return assignmentRepo.findAll()
// .stream()
// .map(this::map)
// .toList();
// }

// // 🔁 MAPPER
// private AssignmentResponse map(VehicleDriverAssignment e) {

// AssignmentResponse res = new AssignmentResponse();

// res.setId(e.getId());

// if (e.getVehicle() != null) {
// res.setVehicleId(e.getVehicle().getId());
// res.setPlateNumber(e.getVehicle().getPlateNumber());
// }

// if (e.getDriver() != null) {
// res.setDriverId(e.getDriver().getId());
// res.setDriverName(e.getDriver().getFullName());
// }

// res.setAssignedDate(e.getAssignedDate());

// return res;
// }
// }