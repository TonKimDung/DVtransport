package com.transport.backend.dto.license_type;

public class UpdateLicenseTypeRequest {

    private Double baseSalary;

    private String description;

    public Double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(Double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}