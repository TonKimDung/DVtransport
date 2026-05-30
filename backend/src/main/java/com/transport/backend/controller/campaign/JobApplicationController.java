package com.transport.backend.controller.campaign;

import java.util.List;

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

import com.transport.backend.dto.campaign.JobApplicationDTO;
import com.transport.backend.dto.campaign.JobApplicationResponse;
import com.transport.backend.service.campaign.JobApplicationService;

@RestController
@RequestMapping("/api/job-applications")
public class JobApplicationController {

    private final JobApplicationService service;

    public JobApplicationController(JobApplicationService service) {
        this.service = service;
    }

    // Ứng tuyển công khai, không cần quyền
    @PostMapping
    public JobApplicationResponse create(@RequestBody JobApplicationDTO dto) {
        return service.create(dto);
    }

    // Chỉ Admin và HR được xem
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    public List<JobApplicationResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    public JobApplicationResponse getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @GetMapping("/campaign/{campaignId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    public List<JobApplicationResponse> getByCampaign(
            @PathVariable Integer campaignId
    ) {
        return service.getByCampaign(campaignId);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    public JobApplicationResponse update(
            @PathVariable Integer id,
            @RequestBody JobApplicationDTO dto
    ) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    public JobApplicationResponse updateStatus(
            @PathVariable Integer id,
            @RequestParam String status
    ) {
        return service.updateStatus(id, status);
    }
}