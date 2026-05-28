package com.transport.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.transport.backend.dto.license_type.CreateLicenseTypeRequest;
import com.transport.backend.dto.license_type.LicenseTypeDTO;
import com.transport.backend.dto.license_type.UpdateLicenseTypeRequest;
import com.transport.backend.service.LicenseTypeService;

@RestController
@RequestMapping("/api/license-types")
@CrossOrigin("*")
public class LicenseTypeController {

    @Autowired
    private LicenseTypeService service;

    // =========================
    // CREATE
    // =========================

    @PostMapping
    public LicenseTypeDTO create(
            @RequestBody CreateLicenseTypeRequest req) {

        return service.create(req);
    }

    // =========================
    // GET ALL
    // =========================

    @GetMapping
    public List<LicenseTypeDTO> getAll() {

        return service.getAll();
    }

    // =========================
    // GET BY ID
    // =========================

    @GetMapping("/{id}")
    public LicenseTypeDTO getById(
            @PathVariable Integer id) {

        return service.getById(id);
    }

    // =========================
    // UPDATE
    // =========================

    @PutMapping("/{id}")
    public LicenseTypeDTO update(
            @PathVariable Integer id,

            @RequestBody UpdateLicenseTypeRequest req) {

        return service.update(
                id,
                req);
    }

    // =========================
    // DELETE
    // =========================

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Integer id) {

        service.delete(id);
    }
}