package com.transport.backend.repository.CampaignRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.transport.backend.entity.JobApplication;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Integer> {

    List<JobApplication> findByCampaign_Id(Integer campaignId);

}