package com.transport.backend.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "job_applications")
public class JobApplication {

    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id", nullable = false)
    private RecruitmentCampaign campaign;

    @Column(name = "full_name", nullable = false, length = 255)
    private String fullName;

    @Column(length = 255)
    private String phone;

    @Column(length = 255)
    private String email;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(name = "experience_years")
    private Integer experienceYears;

    @Column(length = 255)
    private String status;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public JobApplication() {
    }

    public JobApplication( RecruitmentCampaign campaign, String fullName, String phone, String email,
            String address, Integer experienceYears, String status, LocalDateTime createdAt) {
        
        this.campaign = campaign;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.experienceYears = experienceYears;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RecruitmentCampaign getCampaign() {
        return campaign;
    }

    public void setCampaign(RecruitmentCampaign campaign) {
        this.campaign = campaign;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}