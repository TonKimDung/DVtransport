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

import com.transport.backend.dto.document.DocumentRequest;
import com.transport.backend.dto.document.DocumentResponse;
import com.transport.backend.service.DocumentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
@CrossOrigin("*")
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping
    public List<DocumentResponse> getAllDocuments() {
        return documentService.getAllDocuments();
    }

    @GetMapping("/{id}")
    public DocumentResponse getDocumentById(@PathVariable Integer id) {
        return documentService.getDocumentById(id);
    }

    @PostMapping
    public DocumentResponse createDocument(@RequestBody DocumentRequest request) {
        return documentService.createDocument(request);
    }

    @PutMapping("/{id}")
    public DocumentResponse updateDocument(@PathVariable Integer id, @RequestBody DocumentRequest request) {
        return documentService.updateDocument(id, request);
    }

    @GetMapping("/expiring")
    public List<DocumentResponse> getExpiringDocuments() {
        return documentService.getExpiringDocuments();
    }

    @DeleteMapping("/{id}")
    public String deleteDocument(@PathVariable Integer id) {
        documentService.deleteDocument(id);
        return "Xóa chứng từ thành công";
    }
}