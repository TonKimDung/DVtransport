package com.transport.backend.service;

import com.transport.backend.dto.document.DocumentRequest;
import com.transport.backend.dto.document.DocumentResponse;
import com.transport.backend.entity.*;
import com.transport.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;
    private final ContractRepository contractRepository;
    private final OrderRepository orderRepository;
    private final TripRepository tripRepository;

    private DocumentResponse toResponse(Document document) {
        LocalDate today = LocalDate.now();
        String expiryStatus = "Không có hạn";

        if (document.getExpiryDate() != null) {
            if (document.getExpiryDate().isBefore(today)) {
                expiryStatus = "Đã hết hạn";
            } else if (!document.getExpiryDate().isAfter(today.plusDays(30))) {
                expiryStatus = "Sắp hết hạn";
            } else {
                expiryStatus = "Còn hạn";
            }
        }

        return DocumentResponse.builder()
                .id(document.getId())
                .documentType(document.getDocumentType())
                .documentName(document.getDocumentName())
                .fileUrl(document.getFileUrl())

                .driverId(document.getDriver() != null ? document.getDriver().getId() : null)
                .driverName(document.getDriver() != null ? document.getDriver().getFullName() : null)

                .vehicleId(document.getVehicle() != null ? document.getVehicle().getId() : null)
                .plateNumber(document.getVehicle() != null ? document.getVehicle().getPlateNumber() : null)

                .contractId(document.getContract() != null ? document.getContract().getId() : null)

                .orderId(document.getOrder() != null ? document.getOrder().getId() : null)
                .orderCode(document.getOrder() != null ? document.getOrder().getOrderCode() : null)

                .tripId(document.getTrip() != null ? document.getTrip().getId() : null)
                .tripCode(document.getTrip() != null ? document.getTrip().getTripCode() : null)

                .issueDate(document.getIssueDate())
                .expiryDate(document.getExpiryDate())
                .createdAt(document.getCreatedAt())
                .expiryStatus(expiryStatus)
                .build();
    }

    public List<DocumentResponse> getAllDocuments() {
        return documentRepository.findAll().stream().map(this::toResponse).toList();
    }

    public DocumentResponse getDocumentById(Integer id) {
        return toResponse(findDocumentEntityById(id));
    }

    private Document findDocumentEntityById(Integer id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chứng từ"));
    }

    public DocumentResponse createDocument(DocumentRequest request) {
        Driver driver = request.getDriverId() != null
                ? driverRepository.findById(request.getDriverId()).orElseThrow(() -> new RuntimeException("Không tìm thấy tài xế"))
                : null;

        Vehicle vehicle = request.getVehicleId() != null
                ? vehicleRepository.findById(request.getVehicleId()).orElseThrow(() -> new RuntimeException("Không tìm thấy phương tiện"))
                : null;

        Contract contract = request.getContractId() != null
                ? contractRepository.findById(request.getContractId()).orElseThrow(() -> new RuntimeException("Không tìm thấy hợp đồng"))
                : null;

        Order order = request.getOrderId() != null
                ? orderRepository.findById(request.getOrderId()).orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"))
                : null;

        Trip trip = request.getTripId() != null
                ? tripRepository.findById(request.getTripId()).orElseThrow(() -> new RuntimeException("Không tìm thấy chuyến đi"))
                : null;

        Document document = Document.builder()
                .documentType(request.getDocumentType())
                .documentName(request.getDocumentName())
                .fileUrl(request.getFileUrl())
                .driver(driver)
                .vehicle(vehicle)
                .contract(contract)
                .order(order)
                .trip(trip)
                .issueDate(request.getIssueDate())
                .expiryDate(request.getExpiryDate())
                .build();

        return toResponse(documentRepository.save(document));
    }

    public DocumentResponse updateDocument(Integer id, DocumentRequest request) {
        Document document = findDocumentEntityById(id);

        Driver driver = request.getDriverId() != null
                ? driverRepository.findById(request.getDriverId()).orElseThrow(() -> new RuntimeException("Không tìm thấy tài xế"))
                : null;

        Vehicle vehicle = request.getVehicleId() != null
                ? vehicleRepository.findById(request.getVehicleId()).orElseThrow(() -> new RuntimeException("Không tìm thấy phương tiện"))
                : null;

        Contract contract = request.getContractId() != null
                ? contractRepository.findById(request.getContractId()).orElseThrow(() -> new RuntimeException("Không tìm thấy hợp đồng"))
                : null;

        Order order = request.getOrderId() != null
                ? orderRepository.findById(request.getOrderId()).orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"))
                : null;

        Trip trip = request.getTripId() != null
                ? tripRepository.findById(request.getTripId()).orElseThrow(() -> new RuntimeException("Không tìm thấy chuyến đi"))
                : null;

        document.setDocumentType(request.getDocumentType());
        document.setDocumentName(request.getDocumentName());
        document.setFileUrl(request.getFileUrl());
        document.setDriver(driver);
        document.setVehicle(vehicle);
        document.setContract(contract);
        document.setOrder(order);
        document.setTrip(trip);
        document.setIssueDate(request.getIssueDate());
        document.setExpiryDate(request.getExpiryDate());

        return toResponse(documentRepository.save(document));
    }

    public List<DocumentResponse> getExpiringDocuments() {
        LocalDate today = LocalDate.now();
        return documentRepository.findByExpiryDateBetween(today, today.plusDays(30))
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public void deleteDocument(Integer id) {
        documentRepository.delete(findDocumentEntityById(id));
    }
}