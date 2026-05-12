package com.transport.backend.controller.SEM;

import com.transport.backend.dto.SEM.ApiResponse;
import com.transport.backend.service.SEM.SemService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sem")
public class SemController {

    private final SemService semService;

    public SemController(SemService semService) {
        this.semService = semService;
    }

    @GetMapping
    public ApiResponse<?> getSem() {
        return semService.getSemResult();
    }
}