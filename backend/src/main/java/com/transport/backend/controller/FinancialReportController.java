package com.transport.backend.controller;

import com.transport.backend.dto.report.FinancialReportResponse;
import com.transport.backend.service.FinancialReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/financial-reports")
@RequiredArgsConstructor
@CrossOrigin("*")
public class FinancialReportController {

    private final FinancialReportService financialReportService;

    @GetMapping("/monthly")
    public FinancialReportResponse getMonthlyReport(
            @RequestParam Integer month,
            @RequestParam Integer year
    ) {
        return financialReportService.getMonthlyReport(month, year);
    }
}