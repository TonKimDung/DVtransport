package com.transport.backend.dto.contract;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ContractRequest {

    public String contractNumber;
    public String contractType;
    public Integer customerId;
    public Integer partnerId;
    public LocalDate startDate;
    public LocalDate endDate;
    public BigDecimal totalValue;
    public String status;
}