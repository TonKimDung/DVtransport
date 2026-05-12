package com.transport.backend.dto.contract;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ContractResponse {

    public Integer id;
    public String contractNumber;
    public String contractType;

    public String customerName;
    public String partnerName;

    public LocalDate startDate;
    public LocalDate endDate;
    public BigDecimal totalValue;
    public String status;
}