// package com.transport.backend.controller.driver_assignment;

// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.transport.backend.dto.driver_assignment.AssignDriverRequest;
// import com.transport.backend.dto.driver_assignment.DriverWorkResponse;
// import com.transport.backend.entity.VehicleDriverAssignment;
// import
// com.transport.backend.service.driver_assignment.DriverAssignmentService;

// @RestController
// @RequestMapping("/api/driver")
// public class DriverAssignmentController {

// private final DriverAssignmentService service;

// public DriverAssignmentController(DriverAssignmentService service) {
// this.service = service;
// }

// @PostMapping("/assign")
// public VehicleDriverAssignment assign(@RequestBody AssignDriverRequest req) {
// return service.assign(req.vehicleId, req.driverId);
// }

// @GetMapping("/{driverId}/work")
// public DriverWorkResponse work(@PathVariable Integer driverId) {
// return service.getWork(driverId);
// }
// }