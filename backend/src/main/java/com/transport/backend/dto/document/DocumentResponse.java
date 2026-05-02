package com.transport.backend.dto.document;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentResponse {
    private Integer id;
    private String documentType;
    private String documentName;
    private String fileUrl;

    private Integer driverId;
    private String driverName;

    private Integer vehicleId;
    private String plateNumber;

    private Integer contractId;
    private Integer orderId;
    private String orderCode;

    private Integer tripId;
    private String tripCode;

    private LocalDate issueDate;
    private LocalDate expiryDate;
    private LocalDateTime createdAt;

    private String expiryStatus;
}
