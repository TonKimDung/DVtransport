package com.transport.backend.controller.contract;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.transport.backend.dto.contract.*;
import com.transport.backend.service.contract.ContractService;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    private final ContractService service;

    public ContractController(ContractService service) {
        this.service = service;
    }

    @PostMapping
    public ContractResponse create(@RequestBody ContractRequest req) {
        return service.create(req);
    }

    @GetMapping
    public List<ContractResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ContractResponse getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}