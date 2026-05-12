package com.transport.backend.service.campaign;

import com.transport.backend.dto.campaign.*;
import com.transport.backend.entity.RecruitmentCampaign;
import com.transport.backend.repository.CampaignRepository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecruitmentCampaignService {

    private final RecruitmentCampaignRepository repository;

    public RecruitmentCampaignService(RecruitmentCampaignRepository repository) {
        this.repository = repository;
    }

    // CREATE
    public RecruitmentCampaign create(RecruitmentCampaignDTO dto) {
        RecruitmentCampaign campaign = new RecruitmentCampaign();
        campaign.setTitle(dto.getTitle());
        campaign.setDescription(dto.getDescription());
        campaign.setStartDate(dto.getStartDate());
        campaign.setEndDate(dto.getEndDate());
        campaign.setStatus(dto.getStatus());

        return repository.save(campaign);
    }

    // GET ALL
    public List<RecruitmentCampaign> getAll() {
        return repository.findAll();
    }

    // GET DETAIL
    public RecruitmentCampaign getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign not found"));
    }

    // UPDATE
    public RecruitmentCampaign update(Integer id, RecruitmentCampaignDTO dto) {
        RecruitmentCampaign campaign = getById(id);

        campaign.setTitle(dto.getTitle());
        campaign.setDescription(dto.getDescription());
        campaign.setStartDate(dto.getStartDate());
        campaign.setEndDate(dto.getEndDate());
        campaign.setStatus(dto.getStatus());

        return repository.save(campaign);
    }

    // DELETE
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}