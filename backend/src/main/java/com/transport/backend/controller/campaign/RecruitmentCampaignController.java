package com.transport.backend.controller.campaign;

import com.transport.backend.dto.campaign.RecruitmentCampaignDTO;
import com.transport.backend.entity.RecruitmentCampaign;
import com.transport.backend.service.campaign.RecruitmentCampaignService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
}