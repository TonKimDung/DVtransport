package com.transport.backend.service.campaign;

import com.transport.backend.dto.campaign.JobApplicationDTO;
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

    // CREATE
    public JobApplication create(JobApplicationDTO dto) {

        RecruitmentCampaign campaign = campaignRepository.findById(dto.getCampaignId())
                .orElseThrow(() -> new RuntimeException("Campaign not found"));

        JobApplication app = new JobApplication();
        app.setCampaign(campaign); // 🔥 QUAN TRỌNG
        app.setFullName(dto.getFullName());
        app.setPhone(dto.getPhone());
        app.setEmail(dto.getEmail());
        app.setAddress(dto.getAddress());
        app.setExperienceYears(dto.getExperienceYears());
        app.setStatus(dto.getStatus());

        return repository.save(app);
    }

    // GET ALL
    public List<JobApplication> getAll() {
        return repository.findAll();
    }

    // GET BY CAMPAIGN
    public List<JobApplication> getByCampaign(Integer campaignId) {
        return repository.findByCampaign_Id(campaignId);
    }

    // GET DETAIL
    public JobApplication getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
    }

    // UPDATE
    public JobApplication update(Integer id, JobApplicationDTO dto) {

        JobApplication app = getById(id);

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

        return repository.save(app);
    }

    // DELETE
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}