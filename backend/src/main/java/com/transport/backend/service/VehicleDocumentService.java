package com.transport.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.transport.backend.dto.vehicledocument.VehicleDocumentRequest;
import com.transport.backend.dto.vehicledocument.VehicleDocumentResponse;
import com.transport.backend.entity.Vehicle;
import com.transport.backend.entity.VehicleDocument;
import com.transport.backend.repository.VehicleDocumentRepository;
import com.transport.backend.repository.VehicleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VehicleDocumentService {

    private final VehicleDocumentRepository vehicleDocumentRepository;
    private final VehicleRepository vehicleRepository;

    private VehicleDocumentResponse toResponse(VehicleDocument document) {
        return VehicleDocumentResponse.builder()
                .id(document.getId())
                .vehicleId(document.getVehicle() != null ? document.getVehicle().getId() : null)
                .plateNumber(document.getVehicle() != null ? document.getVehicle().getPlateNumber() : null)
                .documentType(document.getDocumentType())
                .documentName(document.getDocumentName())
                .fileUrl(document.getFileUrl())
                .issueDate(document.getIssueDate())
                .expiryDate(document.getExpiryDate())
                .status(document.getStatus())
                .createdAt(document.getCreatedAt())
                .build();
    }

    public List<VehicleDocumentResponse> getAllDocuments() {
        return vehicleDocumentRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public VehicleDocumentResponse getDocumentById(Integer id) {
        VehicleDocument document = vehicleDocumentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giấy tờ xe"));
        return toResponse(document);
    }

    public List<VehicleDocumentResponse> getDocumentsByVehicle(Integer vehicleId) {
        return vehicleDocumentRepository.findByVehicleId(vehicleId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public VehicleDocumentResponse createDocument(VehicleDocumentRequest request) {
        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phương tiện"));

        VehicleDocument document = VehicleDocument.builder()
                .vehicle(vehicle)
                .documentType(request.getDocumentType())
                .documentName(request.getDocumentName())
                .fileUrl(request.getFileUrl())
                .issueDate(request.getIssueDate())
                .expiryDate(request.getExpiryDate())
                .status(request.getStatus() != null ? request.getStatus() : "Còn hạn")
                .build();

        return toResponse(vehicleDocumentRepository.save(document));
    }

    public VehicleDocumentResponse updateDocument(Integer id, VehicleDocumentRequest request) {
        VehicleDocument document = vehicleDocumentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giấy tờ xe"));

        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phương tiện"));

        document.setVehicle(vehicle);
        document.setDocumentType(request.getDocumentType());
        document.setDocumentName(request.getDocumentName());
        document.setFileUrl(request.getFileUrl());
        document.setIssueDate(request.getIssueDate());
        document.setExpiryDate(request.getExpiryDate());
        document.setStatus(request.getStatus());

        return toResponse(vehicleDocumentRepository.save(document));
    }

    public void deleteDocument(Integer id) {
        VehicleDocument document = vehicleDocumentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giấy tờ xe"));
        vehicleDocumentRepository.delete(document);
    }
}