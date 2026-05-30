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
import org.springframework.web.bind.annotation.RestController;

import com.transport.backend.dto.campaign.RecruitmentCampaignDTO;
import com.transport.backend.entity.RecruitmentCampaign;
import com.transport.backend.service.campaign.RecruitmentCampaignService;

@RestController
@PreAuthorize("hasAnyRole('ADMIN', 'HR')")
@RequestMapping("/api/recruitment-campaigns")
public class RecruitmentCampaignController {

    private final RecruitmentCampaignService service;

    public RecruitmentCampaignController(RecruitmentCampaignService service) {
        this.service = service;
    }

    @PostMapping
    public RecruitmentCampaign create(@RequestBody RecruitmentCampaignDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<RecruitmentCampaign> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public RecruitmentCampaign getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public RecruitmentCampaign update(@PathVariable Integer id,
            @RequestBody RecruitmentCampaignDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    @PutMapping("/{id}/close")
    public RecruitmentCampaign closeCampaign(
            @PathVariable Integer id) {
        return service.closeCampaign(id);
    }
}