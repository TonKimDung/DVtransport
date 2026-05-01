package com.transport.backend.controller.campaign;

import com.transport.backend.dto.campaign.JobApplicationDTO;
import com.transport.backend.dto.campaign.JobApplicationResponse;
import com.transport.backend.service.campaign.JobApplicationService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-applications")
public class JobApplicationController {

    private final JobApplicationService service;

    public JobApplicationController(JobApplicationService service) {
        this.service = service;
    }

    @PostMapping
    public JobApplicationResponse create(@RequestBody JobApplicationDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<JobApplicationResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public JobApplicationResponse getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @GetMapping("/campaign/{campaignId}")
    public List<JobApplicationResponse> getByCampaign(@PathVariable Integer campaignId) {
        return service.getByCampaign(campaignId);
    }

    @PutMapping("/{id}")
    public JobApplicationResponse update(@PathVariable Integer id,
            @RequestBody JobApplicationDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}