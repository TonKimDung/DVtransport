package com.transport.backend.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.transport.backend.dto.fuel.FuelTransactionRequest;
import com.transport.backend.dto.fuel.FuelTransactionResponse;
import com.transport.backend.entity.Driver;
import com.transport.backend.entity.FuelTransaction;
import com.transport.backend.entity.Partner;
import com.transport.backend.entity.Trip;
import com.transport.backend.entity.Vehicle;
import com.transport.backend.repository.DriverRepository;
import com.transport.backend.repository.FuelTransactionRepository;
import com.transport.backend.repository.contract.PartnerRepository;
import com.transport.backend.repository.TripRepository;
import com.transport.backend.repository.VehicleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FuelTransactionService {

    private final FuelTransactionRepository fuelTransactionRepository;
    private final VehicleRepository vehicleRepository;
    private final TripRepository tripRepository;
    private final DriverRepository driverRepository;
    private final PartnerRepository partnerRepository;

    private FuelTransactionResponse toResponse(FuelTransaction fuel) {
        return FuelTransactionResponse.builder()
                .id(fuel.getId())
                .vehicleId(fuel.getVehicle() != null ? fuel.getVehicle().getId() : null)
                .plateNumber(fuel.getVehicle() != null ? fuel.getVehicle().getPlateNumber() : null)
                .tripId(fuel.getTrip() != null ? fuel.getTrip().getId() : null)
                .driverId(fuel.getDriver() != null ? fuel.getDriver().getId() : null)
                .driverName(fuel.getDriver() != null ? fuel.getDriver().getFullName() : null)
                .partnerId(fuel.getPartner() != null ? fuel.getPartner().getId() : null)
                .partnerName(fuel.getPartner() != null ? fuel.getPartner().getName() : null)
                .fuelDate(fuel.getFuelDate())
                .quantityLiters(fuel.getQuantityLiters())
                .unitPrice(fuel.getUnitPrice())
                .totalAmount(fuel.getTotalAmount())
                .invoiceNumber(fuel.getInvoiceNumber())
                .createdAt(fuel.getCreatedAt())
                .build();
    }

    public List<FuelTransactionResponse> getAllFuelTransactions() {
        return fuelTransactionRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public FuelTransactionResponse getFuelTransactionById(Integer id) {
        FuelTransaction fuel = fuelTransactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu nhiên liệu"));
        return toResponse(fuel);
    }

    private FuelTransaction findFuelEntityById(Integer id) {
        return fuelTransactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu nhiên liệu"));
    }

    public FuelTransactionResponse createFuelTransaction(FuelTransactionRequest request) {
        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phương tiện"));

        Trip trip = null;
        if (request.getTripId() != null) {
            trip = tripRepository.findById(request.getTripId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy chuyến đi"));
        }

        Driver driver = null;
        if (request.getDriverId() != null) {
            driver = driverRepository.findById(request.getDriverId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy tài xế"));
        }

        Partner partner = null;
        if (request.getPartnerId() != null) {
            partner = partnerRepository.findById(request.getPartnerId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy đối tác"));
        }

        BigDecimal totalAmount = request.getQuantityLiters().multiply(request.getUnitPrice());

        FuelTransaction fuel = FuelTransaction.builder()
                .vehicle(vehicle)
                .trip(trip)
                .driver(driver)
                .partner(partner)
                .fuelDate(request.getFuelDate() != null ? request.getFuelDate() : LocalDateTime.now())
                .quantityLiters(request.getQuantityLiters())
                .unitPrice(request.getUnitPrice())
                .totalAmount(totalAmount)
                .invoiceNumber(request.getInvoiceNumber())
                .build();

        return toResponse(fuelTransactionRepository.save(fuel));
    }

    public FuelTransactionResponse updateFuelTransaction(Integer id, FuelTransactionRequest request) {
        FuelTransaction fuel = findFuelEntityById(id);

        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phương tiện"));

        Trip trip = null;
        if (request.getTripId() != null) {
            trip = tripRepository.findById(request.getTripId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy chuyến đi"));
        }

        Driver driver = null;
        if (request.getDriverId() != null) {
            driver = driverRepository.findById(request.getDriverId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy tài xế"));
        }

        Partner partner = null;
        if (request.getPartnerId() != null) {
            partner = partnerRepository.findById(request.getPartnerId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy đối tác"));
        }

        fuel.setVehicle(vehicle);
        fuel.setTrip(trip);
        fuel.setDriver(driver);
        fuel.setPartner(partner);
        fuel.setFuelDate(request.getFuelDate() != null ? request.getFuelDate() : LocalDateTime.now());
        fuel.setQuantityLiters(request.getQuantityLiters());
        fuel.setUnitPrice(request.getUnitPrice());
        fuel.setTotalAmount(request.getQuantityLiters().multiply(request.getUnitPrice()));
        fuel.setInvoiceNumber(request.getInvoiceNumber());

        return toResponse(fuelTransactionRepository.save(fuel));
    }

    public List<FuelTransactionResponse> getFuelHistoryByVehicle(Integer vehicleId) {
        return fuelTransactionRepository.findByVehicleId(vehicleId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<FuelTransactionResponse> getFuelHistoryByDate(LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.plusDays(1).atStartOfDay();

        return fuelTransactionRepository.findByFuelDateBetween(start, end)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public BigDecimal getFuelConsumptionByVehicle(Integer vehicleId, LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.plusDays(1).atStartOfDay();

        List<FuelTransaction> list =
                fuelTransactionRepository.findByVehicleIdAndFuelDateBetween(vehicleId, start, end);

        return list.stream()
                .map(FuelTransaction::getQuantityLiters)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void deleteFuelTransaction(Integer id) {
        FuelTransaction fuel = findFuelEntityById(id);
        fuelTransactionRepository.delete(fuel);
    }
}