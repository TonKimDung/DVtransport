package com.transport.backend.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.transport.backend.dto.report.FinancialReportResponse;
import com.transport.backend.entity.FuelTransaction;
import com.transport.backend.entity.Order;
import com.transport.backend.entity.Payroll;
import com.transport.backend.entity.Trip;
import com.transport.backend.entity.TripExpense;
import com.transport.backend.entity.TripOrder;
import com.transport.backend.repository.FuelTransactionRepository;
import com.transport.backend.repository.PayrollRepository;
import com.transport.backend.repository.TripExpenseRepository;
import com.transport.backend.repository.TripOrderRepository;
import com.transport.backend.repository.TripRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FinancialReportService {

    private final FuelTransactionRepository fuelTransactionRepository;
    private final TripExpenseRepository tripExpenseRepository;
    private final PayrollRepository payrollRepository;
    private final TripRepository tripRepository;
    private final TripOrderRepository tripOrderRepository;

    private BigDecimal safe(BigDecimal value) {
        return value != null ? value : BigDecimal.ZERO;
    }

    public FinancialReportResponse getMonthlyReport(Integer month, Integer year) {

        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime end = start.plusMonths(1);

        List<FuelTransaction> fuels = fuelTransactionRepository.findByFuelDateBetween(start, end);
        List<TripExpense> expenses = tripExpenseRepository.findByCreatedAtBetween(start, end);
        List<Payroll> payrolls = payrollRepository.findByMonthAndYear(month, year);

        // 👉 chỉ lấy trip COMPLETED trong tháng
        List<Trip> trips = tripRepository.findByCreatedAtBetween(start, end);

        // ========================
        // COST
        // ========================

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

        // ========================
        // REVENUE (FIX CHÍNH Ở ĐÂY)
        // ========================

        BigDecimal totalRevenue = BigDecimal.ZERO;
        Set<Integer> countedOrderIds = new HashSet<>();

        for (Trip trip : trips) {

            // 👉 chỉ lấy trip hoàn thành
            if (!"COMPLETED".equalsIgnoreCase(trip.getStatus())) continue;

            List<TripOrder> tripOrders = tripOrderRepository.findByTripId(trip.getId());

            for (TripOrder tripOrder : tripOrders) {
                Order order = tripOrder.getOrder();

                if (order != null
                        && "COMPLETED".equalsIgnoreCase(order.getStatus())
                        && !countedOrderIds.contains(order.getId())) {

                    totalRevenue = totalRevenue.add(safe(order.getTotalAmount()));
                    countedOrderIds.add(order.getId());
                }
            }
        }

        // ========================
        // FINAL
        // ========================

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