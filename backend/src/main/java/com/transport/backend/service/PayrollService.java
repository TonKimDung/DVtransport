package com.transport.backend.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.transport.backend.dto.payroll.PayrollCalculateRequest;
import com.transport.backend.dto.payroll.PayrollResponse;
import com.transport.backend.entity.Driver;
import com.transport.backend.entity.DriverBaseSalary;
import com.transport.backend.entity.Order;
import com.transport.backend.entity.Payroll;
import com.transport.backend.entity.Trip;
import com.transport.backend.entity.TripOrder;
import com.transport.backend.repository.DriverBaseSalaryRepository;
import com.transport.backend.repository.DriverRepository;
import com.transport.backend.repository.PayrollRepository;
import com.transport.backend.repository.TripOrderRepository;
import com.transport.backend.repository.TripRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PayrollService {

    private final PayrollRepository payrollRepository;
    private final DriverRepository driverRepository;
    private final DriverBaseSalaryRepository driverBaseSalaryRepository;
    private final TripRepository tripRepository;
    private final TripOrderRepository tripOrderRepository;

    private static final BigDecimal COMMISSION_RATE = new BigDecimal("0.10");

    private BigDecimal safe(BigDecimal value) {
        return value != null ? value : BigDecimal.ZERO;
    }

    private PayrollResponse toResponse(
            Payroll payroll,
            BigDecimal baseSalary,
            BigDecimal completedOrderAmount,
            BigDecimal commissionAmount
    ) {
        return PayrollResponse.builder()
                .id(payroll.getId())
                .driverId(payroll.getDriver() != null ? payroll.getDriver().getId() : null)
                .driverName(payroll.getDriver() != null ? payroll.getDriver().getFullName() : null)
                .month(payroll.getMonth())
                .year(payroll.getYear())
                .baseSalary(baseSalary)
                .completedOrderAmount(completedOrderAmount)
                .commissionRate(COMMISSION_RATE)
                .commissionAmount(commissionAmount)
                .totalSalary(payroll.getTotalSalary())
                .bonusAmount(payroll.getBonusAmount())
                .penaltyAmount(payroll.getPenaltyAmount())
                .finalAmount(payroll.getFinalAmount())
                .status(payroll.getStatus())
                .createdAt(payroll.getCreatedAt())
                .build();
    }

    public PayrollResponse calculatePayroll(PayrollCalculateRequest request) {
        Driver driver = driverRepository.findById(request.getDriverId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài xế"));

        DriverBaseSalary baseSalaryRecord = driverBaseSalaryRepository
                .findTopByDriverIdAndStatusOrderByCreatedAtDesc(
                        request.getDriverId(),
                        "Active"
                )
                .orElseThrow(() -> new RuntimeException("Tài xế chưa có lương cứng đang áp dụng"));

        BigDecimal baseSalary = safe(baseSalaryRecord.getBaseSalary());

        LocalDateTime start = LocalDateTime.of(request.getYear(), request.getMonth(), 1, 0, 0);
        LocalDateTime end = start.plusMonths(1);

        List<Trip> completedTrips =
                tripRepository.findByDriverIdAndStatusAndArrivalTimeBetween(
                        request.getDriverId(),
                        "COMPLETED",
                        start,
                        end
                );

        BigDecimal completedOrderAmount = BigDecimal.ZERO;
        Set<Integer> countedOrderIds = new HashSet<>();

        for (Trip trip : completedTrips) {
            List<TripOrder> tripOrders = tripOrderRepository.findByTripId(trip.getId());

            for (TripOrder tripOrder : tripOrders) {
                Order order = tripOrder.getOrder();

                if (order != null
                        && "COMPLETED".equalsIgnoreCase(order.getStatus())
                        && !countedOrderIds.contains(order.getId())) {

                    completedOrderAmount = completedOrderAmount.add(safe(order.getTotalAmount()));
                    countedOrderIds.add(order.getId());
                }
            }
        }

        BigDecimal commissionAmount = completedOrderAmount.multiply(COMMISSION_RATE);

        BigDecimal bonusAmount = safe(request.getBonusAmount());
        BigDecimal penaltyAmount = safe(request.getPenaltyAmount());

        BigDecimal totalSalary = baseSalary.add(commissionAmount);
        BigDecimal finalAmount = totalSalary.add(bonusAmount).subtract(penaltyAmount);

        Payroll payroll = payrollRepository
                .findByDriverIdAndMonthAndYear(
                        request.getDriverId(),
                        request.getMonth(),
                        request.getYear()
                )
                .orElse(new Payroll());

        payroll.setDriver(driver);
        payroll.setMonth(request.getMonth());
        payroll.setYear(request.getYear());
        payroll.setTotalSalary(totalSalary);
        payroll.setBonusAmount(bonusAmount);
        payroll.setPenaltyAmount(penaltyAmount);
        payroll.setFinalAmount(finalAmount);
        payroll.setStatus("Calculated");

        if (payroll.getCreatedAt() == null) {
            payroll.setCreatedAt(LocalDateTime.now());
        }

        Payroll saved = payrollRepository.save(payroll);

        return toResponse(saved, baseSalary, completedOrderAmount, commissionAmount);
    }

    public List<PayrollResponse> getPayrollsByMonth(Integer month, Integer year) {
        return payrollRepository.findByMonthAndYear(month, year)
                .stream()
                .map(payroll -> toResponse(payroll, null, null, null))
                .toList();
    }

    public List<PayrollResponse> getPayrollsByDriver(Integer driverId) {
        return payrollRepository.findByDriverId(driverId)
                .stream()
                .map(payroll -> toResponse(payroll, null, null, null))
                .toList();
    }
}