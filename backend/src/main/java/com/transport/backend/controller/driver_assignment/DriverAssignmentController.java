package com.transport.backend.controller.driver_assignment;

import java.security.Principal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PreAuthorize("hasAnyRole('ADMIN', 'DIEU_PHOI_VIEN')")
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

    @PreAuthorize("hasAnyRole('ADMIN', 'DIEU_PHOI_VIEN')")
    @PutMapping("/{id}/deactivate")
    public AssignmentResponse deactivate(
            @PathVariable Integer id) {
        return service
                .deactivate(id);
    }

    @GetMapping("/my")
@PreAuthorize("hasRole('TAI_XE')")
public List<AssignmentResponse> getMyAssignments(Principal principal) {
    return service.getMyAssignments(principal.getName());
}

@GetMapping("/my/{driverId}")
public List<AssignmentResponse> getMyAssignments(
        @PathVariable Integer driverId) {

    return service.getAssignmentsByDriver(driverId);
}
}