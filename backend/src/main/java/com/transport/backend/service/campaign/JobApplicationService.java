package com.transport.backend.service.campaign;

import com.transport.backend.dto.campaign.JobApplicationDTO;
import com.transport.backend.dto.campaign.JobApplicationResponse;
import com.transport.backend.entity.JobApplication;
import com.transport.backend.entity.RecruitmentCampaign;
import com.transport.backend.repository.CampaignRepository.*;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplicationService {

    private final JobApplicationRepository repository;
    private final RecruitmentCampaignRepository campaignRepository;

    public JobApplicationService(JobApplicationRepository repository,
            RecruitmentCampaignRepository campaignRepository) {
        this.repository = repository;
        this.campaignRepository = campaignRepository;
    }

    // ================== MAPPER ==================
    private JobApplicationResponse toResponse(JobApplication app) {
        JobApplicationResponse dto = new JobApplicationResponse();

        dto.setId(app.getId());
        dto.setFullName(app.getFullName());
        dto.setPhone(app.getPhone());
        dto.setEmail(app.getEmail());
        dto.setAddress(app.getAddress());
        dto.setExperienceYears(app.getExperienceYears());
        dto.setStatus(app.getStatus());
        dto.setCreatedAt(app.getCreatedAt());

        if (app.getCampaign() != null) {
            dto.setCampaignId(app.getCampaign().getId());
            dto.setCampaignName(app.getCampaign().getTitle());
        }

        return dto;
    }

    // ================== CREATE ==================
    public JobApplicationResponse create(JobApplicationDTO dto) {

        RecruitmentCampaign campaign = campaignRepository.findById(dto.getCampaignId())
                .orElseThrow(() -> new RuntimeException("Campaign not found"));

        JobApplication app = new JobApplication();
        app.setCampaign(campaign);
        app.setFullName(dto.getFullName());
        app.setPhone(dto.getPhone());
        app.setEmail(dto.getEmail());
        app.setAddress(dto.getAddress());
        app.setExperienceYears(dto.getExperienceYears());
        app.setStatus(dto.getStatus());

        return toResponse(repository.save(app));
    }

    // ================== GET ALL ==================
    public List<JobApplicationResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // ================== GET BY ID ==================
    public JobApplicationResponse getById(Integer id) {
        return toResponse(repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found")));
    }

    // ================== GET BY CAMPAIGN ==================
    public List<JobApplicationResponse> getByCampaign(Integer campaignId) {
        return repository.findByCampaign_Id(campaignId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // ================== UPDATE ==================
    public JobApplicationResponse update(Integer id, JobApplicationDTO dto) {

        JobApplication app = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        if (dto.getCampaignId() != null) {
            RecruitmentCampaign campaign = campaignRepository.findById(dto.getCampaignId())
                    .orElseThrow(() -> new RuntimeException("Campaign not found"));
            app.setCampaign(campaign);
        }

        app.setFullName(dto.getFullName());
        app.setPhone(dto.getPhone());
        app.setEmail(dto.getEmail());
        app.setAddress(dto.getAddress());
        app.setExperienceYears(dto.getExperienceYears());
        app.setStatus(dto.getStatus());

        return toResponse(repository.save(app));
    }

    // ================== DELETE ==================
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}