package com.transport.backend.controller.contract;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.transport.backend.dto.contract.CustomerRequest;
import com.transport.backend.dto.contract.CustomerResponse;
import com.transport.backend.dto.driver.DriverDTO;
import com.transport.backend.service.contract.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    // ✅ Tạo mới
    @PostMapping
    public CustomerResponse create(@RequestBody CustomerRequest req) {
        return service.create(req);
    }

    // ✅ Lấy tất cả
    @GetMapping
    public List<CustomerResponse> getAll() {
        return service.getAll();
    }

    // ✅ Lấy theo id
    @GetMapping("/{id}")
    public CustomerResponse getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    // ✅ Xoá
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "Deleted successfully";
    }

    @GetMapping("/available-contract")
    public List<CustomerResponse> getCustomersWithoutContract() {
        return service.getAvailable();
    }
}