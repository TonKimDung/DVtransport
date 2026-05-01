package com.transport.backend.controller.driver_assignment;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.transport.backend.dto.driver_assignment.AssignDriverRequest;
import com.transport.backend.dto.driver_assignment.AssignmentResponse;
import com.transport.backend.dto.driver_assignment.DriverWorkResponse;
import com.transport.backend.service.driver_assignment.DriverAssignmentService;

@RestController
@RequestMapping("/api/driver-assignments")
public class DriverAssignmentController {

    private final DriverAssignmentService service;

    public DriverAssignmentController(DriverAssignmentService service) {
        this.service = service;
    }

    // 🚗 1. GÁN TÀI XẾ
    @PostMapping
    public AssignmentResponse assign(@RequestBody AssignDriverRequest req) {
        return service.assign(req);
    }

    // 📄 LIST
    @GetMapping
    public List<AssignmentResponse> getAll() {
        return service.getAll();
    }

    // ⏱️ 2 + 3: GIỜ LÀM + CẢNH BÁO
    @GetMapping("/driver/{driverId}/work")
    public DriverWorkResponse getWork(@PathVariable Integer driverId) {
        return service.getWorkToday(driverId);
    }
}