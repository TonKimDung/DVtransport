package com.transport.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.transport.backend.dto.driver_work_log.DriverWorkLogResponse;
import com.transport.backend.service.DriverWorkLogService;

@RestController
@RequestMapping("/api/driver-work-logs")
@CrossOrigin("*")
public class DriverWorkLogController {

    private final DriverWorkLogService service;

    public DriverWorkLogController(
            DriverWorkLogService service) {

        this.service = service;
    }

    @GetMapping
    public List<DriverWorkLogResponse> getAll() {

        return service.getAll();
    }
}