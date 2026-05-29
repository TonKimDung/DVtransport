package com.transport.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transport.backend.dto.license_type.CreateLicenseTypeRequest;
import com.transport.backend.dto.license_type.LicenseTypeDTO;
import com.transport.backend.dto.license_type.UpdateLicenseTypeRequest;
import com.transport.backend.entity.LicenseType;
import com.transport.backend.mapper.LicenseTypeMapper;
import com.transport.backend.repository.LicenseTypeRepository;

@Service
public class LicenseTypeService {

    @Autowired
    private LicenseTypeRepository repository;

    // =========================
    // CREATE
    // =========================

    public LicenseTypeDTO create(
            CreateLicenseTypeRequest req) {

        if (repository.existsByLicenseClass(
                req.getLicenseClass())) {

            throw new RuntimeException(
                    "License class already exists");
        }

        LicenseType l = new LicenseType();

        l.setLicenseClass(
                req.getLicenseClass());

        l.setBaseSalary(
                req.getBaseSalary());

        l.setDescription(
                req.getDescription());

        repository.save(l);

        return LicenseTypeMapper.toDTO(l);
    }

    // =========================
    // GET ALL
    // =========================

    public List<LicenseTypeDTO> getAll() {

        return repository.findAll()
                .stream()
                .map(
                        LicenseTypeMapper::toDTO)
                .toList();
    }

    // =========================
    // GET BY ID
    // =========================

    public LicenseTypeDTO getById(
            Integer id) {

        LicenseType l = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "License type not found"));

        return LicenseTypeMapper.toDTO(l);
    }

    // =========================
    // UPDATE
    // =========================

    public LicenseTypeDTO update(
            Integer id,
            UpdateLicenseTypeRequest req) {

        LicenseType l = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "License type not found"));

        l.setBaseSalary(
                req.getBaseSalary());

        l.setDescription(
                req.getDescription());

        repository.save(l);

        return LicenseTypeMapper.toDTO(l);
    }

    // =========================
    // DELETE
    // =========================

    public void delete(
            Integer id) {

        if (!repository.existsById(id)) {

            throw new RuntimeException(
                    "License type not found");
        }

        repository.deleteById(id);
    }
}