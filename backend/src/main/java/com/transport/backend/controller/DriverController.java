package com.transport.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.transport.backend.dto.driver.CreateDriverRequest;
import com.transport.backend.dto.driver.DriverDTO;
import com.transport.backend.dto.driver.UpdateDriverRequest;
import com.transport.backend.service.DriverService;

@RestController
@PreAuthorize("hasAnyRole('ADMIN', 'DIEU_PHOI_VIEN', 'HR')")
@RequestMapping("/api/drivers")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @GetMapping()
    public List<DriverDTO> getAll() {
        return driverService.getAll();
    }

    @PostMapping
    public DriverDTO create(@RequestBody CreateDriverRequest req) {
        return driverService.create(req);
    }

    @GetMapping("/{id}")
    public DriverDTO getById(@PathVariable Integer id) {
        return driverService.getById(id);
    }

    @PutMapping("/{id}")
    public DriverDTO update(@PathVariable Integer id,
            @RequestBody UpdateDriverRequest req) {
        return driverService.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        driverService.delete(id);
    }

    @PutMapping("/{id}/status")
    public DriverDTO updateStatus(
            @PathVariable Integer id,
            @RequestParam String status) {
        return driverService
                .updateStatus(id, status);
    }

    @GetMapping("/available")
    public List<DriverDTO> getAvailableDrivers() {

        return driverService
                .getAvailableDrivers();
    }

    @GetMapping("/available-contract")
    public List<DriverDTO> getDriversWithoutContract() {
        return driverService.getDriversWithoutContract();
    }
}