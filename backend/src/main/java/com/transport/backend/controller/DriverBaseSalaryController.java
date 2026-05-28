package com.transport.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transport.backend.dto.driverbasesalary.DriverBaseSalaryRequest;
import com.transport.backend.dto.driverbasesalary.DriverBaseSalaryResponse;
import com.transport.backend.service.DriverBaseSalaryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/driver-base-salaries")
@RequiredArgsConstructor
@CrossOrigin("*")
public class DriverBaseSalaryController {

    private final DriverBaseSalaryService driverBaseSalaryService;

    @GetMapping
    public List<DriverBaseSalaryResponse> getAllBaseSalaries() {
        return driverBaseSalaryService.getAllBaseSalaries();
    }

    @GetMapping("/{id}")
    public DriverBaseSalaryResponse getBaseSalaryById(@PathVariable Integer id) {
        return driverBaseSalaryService.getBaseSalaryById(id);
    }

    @GetMapping("/driver/{driverId}")
    public List<DriverBaseSalaryResponse> getBaseSalariesByDriver(@PathVariable Integer driverId) {
        return driverBaseSalaryService.getBaseSalariesByDriver(driverId);
    }

    @PostMapping
    public DriverBaseSalaryResponse createBaseSalary(@RequestBody DriverBaseSalaryRequest request) {
        return driverBaseSalaryService.createBaseSalary(request);
    }

    @PutMapping("/{id}")
    public DriverBaseSalaryResponse updateBaseSalary(
            @PathVariable Integer id,
            @RequestBody DriverBaseSalaryRequest request
    ) {
        return driverBaseSalaryService.updateBaseSalary(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteBaseSalary(@PathVariable Integer id) {
        driverBaseSalaryService.deleteBaseSalary(id);
        return "Xóa lương cứng thành công";
    }
}