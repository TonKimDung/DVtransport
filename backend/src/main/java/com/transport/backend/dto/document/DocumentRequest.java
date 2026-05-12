package com.transport.backend.dto.document;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentRequest {
    private String documentType;
    private String documentName;
    private String fileUrl;

    private Integer driverId;
    private Integer vehicleId;
    private Integer contractId;
    private Integer orderId;
    private Integer tripId;

    private LocalDate issueDate;
    private LocalDate expiryDate;
}