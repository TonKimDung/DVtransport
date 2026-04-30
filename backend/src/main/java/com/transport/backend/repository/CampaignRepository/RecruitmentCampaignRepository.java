package com.transport.backend.repository.CampaignRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.transport.backend.entity.RecruitmentCampaign;

public interface RecruitmentCampaignRepository extends JpaRepository<RecruitmentCampaign, Integer> {
}