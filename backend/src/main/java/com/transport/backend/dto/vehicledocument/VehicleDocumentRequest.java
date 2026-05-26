package com.transport.backend.dto.vehicledocument;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDocumentRequest {
    private Integer vehicleId;
    private String documentType;
    private String documentName;
    private String fileUrl;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String status;
}