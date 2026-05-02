package com.transport.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.transport.backend.dto.incident.IncidentRequest;
import com.transport.backend.dto.incident.IncidentResponse;
import com.transport.backend.service.IncidentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/incidents")
@RequiredArgsConstructor
@CrossOrigin("*")
public class IncidentController {

    private final IncidentService incidentService;

    @GetMapping
    public List<IncidentResponse> getAllIncidents() {
        return incidentService.getAllIncidents();
    }

    @GetMapping("/{id}")
    public IncidentResponse getIncidentById(@PathVariable Integer id) {
        return incidentService.getIncidentById(id);
    }

    @PostMapping
    public IncidentResponse createIncident(@RequestBody IncidentRequest request) {
        return incidentService.createIncident(request);
    }

    @PutMapping("/{id}")
    public IncidentResponse updateIncident(@PathVariable Integer id, @RequestBody IncidentRequest request) {
        return incidentService.updateIncident(id, request);
    }

    @GetMapping("/status")
    public List<IncidentResponse> getIncidentsByStatus(@RequestParam String status) {
        return incidentService.getIncidentsByStatus(status);
    }

    @PatchMapping("/{id}/status")
    public IncidentResponse updateIncidentStatus(@PathVariable Integer id, @RequestParam String status) {
        return incidentService.updateIncidentStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public String deleteIncident(@PathVariable Integer id) {
        incidentService.deleteIncident(id);
        return "Xóa sự cố thành công";
    }
}