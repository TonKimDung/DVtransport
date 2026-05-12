package com.transport.backend.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.transport.backend.dto.fuel.FuelTransactionRequest;
import com.transport.backend.dto.fuel.FuelTransactionResponse;
import com.transport.backend.service.FuelTransactionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/fuel-transactions")
@RequiredArgsConstructor
@CrossOrigin("*")
public class FuelTransactionController {

    private final FuelTransactionService fuelTransactionService;

    @GetMapping
    public List<FuelTransactionResponse> getAllFuelTransactions() {
        return fuelTransactionService.getAllFuelTransactions();
    }

    @GetMapping("/{id}")
    public FuelTransactionResponse getFuelTransactionById(@PathVariable Integer id) {
        return fuelTransactionService.getFuelTransactionById(id);
    }

    @PostMapping
    public FuelTransactionResponse createFuelTransaction(@RequestBody FuelTransactionRequest request) {
        return fuelTransactionService.createFuelTransaction(request);
    }

    @PutMapping("/{id}")
    public FuelTransactionResponse updateFuelTransaction(
            @PathVariable Integer id,
            @RequestBody FuelTransactionRequest request
    ) {
        return fuelTransactionService.updateFuelTransaction(id, request);
    }

    @GetMapping("/vehicle/{vehicleId}")
    public List<FuelTransactionResponse> getFuelHistoryByVehicle(@PathVariable Integer vehicleId) {
        return fuelTransactionService.getFuelHistoryByVehicle(vehicleId);
    }

    @GetMapping("/history")
    public List<FuelTransactionResponse> getFuelHistoryByDate(
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        return fuelTransactionService.getFuelHistoryByDate(
                LocalDate.parse(startDate),
                LocalDate.parse(endDate)
        );
    }

    @GetMapping("/consumption/vehicle/{vehicleId}")
    public BigDecimal getFuelConsumptionByVehicle(
            @PathVariable Integer vehicleId,
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        return fuelTransactionService.getFuelConsumptionByVehicle(
                vehicleId,
                LocalDate.parse(startDate),
                LocalDate.parse(endDate)
        );
    }

    @DeleteMapping("/{id}")
    public String deleteFuelTransaction(@PathVariable Integer id) {
        fuelTransactionService.deleteFuelTransaction(id);
        return "Xóa phiếu nhiên liệu thành công";
    }
}