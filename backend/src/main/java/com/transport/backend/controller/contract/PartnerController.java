package com.transport.backend.controller.contract;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.transport.backend.dto.contract.CustomerResponse;
import com.transport.backend.dto.contract.PartnerRequest;
import com.transport.backend.dto.contract.PartnerResponse;
import com.transport.backend.service.contract.PartnerService;

@RestController
@RequestMapping("/api/partners")
public class PartnerController {

    private final PartnerService service;

    public PartnerController(PartnerService service) {
        this.service = service;
    }

    // ✅ Tạo mới
    @PostMapping
    public PartnerResponse create(@RequestBody PartnerRequest req) {
        return service.create(req);
    }

    // ✅ Lấy tất cả
    @GetMapping
    public List<PartnerResponse> getAll() {
        return service.getAll();
    }

    // (optional) lấy theo id
    @GetMapping("/{id}")
    public PartnerResponse getById(@PathVariable Integer id) {
        return service.getAll()
                .stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Partner not found"));
    }

    @GetMapping("/available-contract")
    public List<PartnerResponse> getPartnersWithoutContract() {
        return service.getAvailable();
    }
}