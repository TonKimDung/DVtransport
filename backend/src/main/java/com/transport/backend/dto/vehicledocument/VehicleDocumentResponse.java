package com.transport.backend.dto.vehicledocument;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleDocumentResponse {
    private Integer id;
    private Integer vehicleId;
    private String plateNumber;
    private String documentType;
    private String documentName;
    private String fileUrl;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String status;
    private LocalDateTime createdAt;
}