package com.transport.backend.repository.CampaignRepository;

import com.transport.backend.entity.RecruitmentCampaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitmentCampaignRepository extends JpaRepository<RecruitmentCampaign, Integer> {
}