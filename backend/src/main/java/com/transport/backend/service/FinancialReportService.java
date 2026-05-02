package com.transport.backend.service;

import com.transport.backend.dto.report.FinancialReportResponse;
import com.transport.backend.entity.FuelTransaction;
import com.transport.backend.entity.Payroll;
import com.transport.backend.entity.Trip;
import com.transport.backend.entity.TripExpense;
import com.transport.backend.repository.FuelTransactionRepository;
import com.transport.backend.repository.PayrollRepository;
import com.transport.backend.repository.TripExpenseRepository;
import com.transport.backend.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FinancialReportService {

    private final FuelTransactionRepository fuelTransactionRepository;
    private final TripExpenseRepository tripExpenseRepository;
    private final PayrollRepository payrollRepository;
    private final TripRepository tripRepository;

    public FinancialReportResponse getMonthlyReport(Integer month, Integer year) {

        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime end = start.plusMonths(1);

        List<FuelTransaction> fuels = fuelTransactionRepository.findByFuelDateBetween(start, end);
        List<TripExpense> expenses = tripExpenseRepository.findByCreatedAtBetween(start, end);
        List<Payroll> payrolls = payrollRepository.findByMonthAndYear(month, year);
        List<Trip> trips = tripRepository.findByCreatedAtBetween(start, end);

        // ✅ chỉ lấy những cái KHÔNG NULL
        BigDecimal totalFuelCost = fuels.stream()
                .map(FuelTransaction::getTotalAmount)
                .filter(v -> v != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalTripExpense = expenses.stream()
                .map(TripExpense::getAmount)
                .filter(v -> v != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPayroll = payrolls.stream()
                .map(Payroll::getFinalAmount)
                .filter(v -> v != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalRevenue = trips.stream()
                .map(Trip::getTotalRevenue)
                .filter(v -> v != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalCost = totalFuelCost
                .add(totalTripExpense)
                .add(totalPayroll);

        BigDecimal profit = totalRevenue.subtract(totalCost);

        return FinancialReportResponse.builder()
                .month(month)
                .year(year)
                .totalFuelCost(totalFuelCost)
                .totalTripExpense(totalTripExpense)
                .totalPayroll(totalPayroll)
                .totalCost(totalCost)
                .totalRevenue(totalRevenue)
                .profit(profit)
                .build();
    }
}