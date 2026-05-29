package com.transport.backend.service.contract;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.transport.backend.dto.contract.CustomerRequest;
import com.transport.backend.dto.contract.CustomerResponse;
import com.transport.backend.entity.Customer;
import com.transport.backend.repository.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository repo;

    public CustomerService(CustomerRepository repo) {
        this.repo = repo;
    }

    public CustomerResponse create(CustomerRequest req) {
        Customer c = new Customer();
        c.setName(req.getName());
        c.setPhone(req.getPhone());
        c.setEmail(req.getEmail());
        c.setAddress(req.getAddress());
        c.setStatus(req.getStatus());

        return mapToResponse(repo.save(c));
    }

    public List<CustomerResponse> getAll() {
        return repo.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<CustomerResponse> getAvailable() {
        return repo.findCustomersWithoutContract()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public CustomerResponse getById(Integer id) {
        Customer c = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return mapToResponse(c);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

    private CustomerResponse mapToResponse(Customer c) {
        CustomerResponse res = new CustomerResponse();
        res.setId(c.getId());
        res.setName(c.getName());
        res.setPhone(c.getPhone());
        res.setEmail(c.getEmail());
        res.setAddress(c.getAddress());
        res.setStatus(c.getStatus());
        res.setCreatedAt(c.getCreatedAt());
        return res;
    }
}