package com.transport.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.transport.backend.dto.payroll.PayrollCalculateRequest;
import com.transport.backend.dto.payroll.PayrollResponse;
import com.transport.backend.service.PayrollService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payrolls")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PayrollController {

    private final PayrollService payrollService;

    @PostMapping("/calculate")
    public PayrollResponse calculatePayroll(@RequestBody PayrollCalculateRequest request) {
        return payrollService.calculatePayroll(request);
    }

    @GetMapping("/monthly")
    public List<PayrollResponse> getPayrollsByMonth(
            @RequestParam Integer month,
            @RequestParam Integer year
    ) {
        return payrollService.getPayrollsByMonth(month, year);
    }

    @GetMapping("/driver/{driverId}")
    public List<PayrollResponse> getPayrollsByDriver(@PathVariable Integer driverId) {
        return payrollService.getPayrollsByDriver(driverId);
    }
}