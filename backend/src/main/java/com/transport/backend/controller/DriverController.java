package com.transport.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.transport.backend.service.DriverService;
import com.transport.backend.dto.driver.CreateDriverRequest;
import com.transport.backend.dto.driver.UpdateDriverRequest;
import com.transport.backend.dto.driver.DriverDTO;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    @Autowired
    private DriverService driverService;

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
}