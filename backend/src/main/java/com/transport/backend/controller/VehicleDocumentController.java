package com.transport.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transport.backend.dto.vehicledocument.VehicleDocumentRequest;
import com.transport.backend.dto.vehicledocument.VehicleDocumentResponse;
import com.transport.backend.service.VehicleDocumentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/vehicle-documents")
@RequiredArgsConstructor
@CrossOrigin("*")
public class VehicleDocumentController {

    private final VehicleDocumentService vehicleDocumentService;

    @GetMapping
    public List<VehicleDocumentResponse> getAllDocuments() {
        return vehicleDocumentService.getAllDocuments();
    }

    @GetMapping("/{id}")
    public VehicleDocumentResponse getDocumentById(@PathVariable Integer id) {
        return vehicleDocumentService.getDocumentById(id);
    }

    @GetMapping("/vehicle/{vehicleId}")
    public List<VehicleDocumentResponse> getDocumentsByVehicle(@PathVariable Integer vehicleId) {
        return vehicleDocumentService.getDocumentsByVehicle(vehicleId);
    }

    @PostMapping
    public VehicleDocumentResponse createDocument(@RequestBody VehicleDocumentRequest request) {
        return vehicleDocumentService.createDocument(request);
    }

    @PutMapping("/{id}")
    public VehicleDocumentResponse updateDocument(
            @PathVariable Integer id,
            @RequestBody VehicleDocumentRequest request
    ) {
        return vehicleDocumentService.updateDocument(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteDocument(@PathVariable Integer id) {
        vehicleDocumentService.deleteDocument(id);
        return "Xóa giấy tờ xe thành công";
    }
}