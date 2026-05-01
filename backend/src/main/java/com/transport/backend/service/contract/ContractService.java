package com.transport.backend.service.contract;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.transport.backend.dto.contract.*;
import com.transport.backend.entity.*;
import com.transport.backend.repository.ContractRepository;
import com.transport.backend.repository.CustomerRepository;
import com.transport.backend.repository.contract.*;

@Service
public class ContractService {

    private final ContractRepository contractRepo;
    private final CustomerRepository customerRepo;
    private final PartnerRepository partnerRepo;

    public ContractService(
            ContractRepository contractRepo,
            CustomerRepository customerRepo,
            PartnerRepository partnerRepo) {
        this.contractRepo = contractRepo;
        this.customerRepo = customerRepo;
        this.partnerRepo = partnerRepo;
    }

    // CREATE
    public ContractResponse create(ContractRequest req) {

        Customer customer = null;
        Partner partner = null;

        if (req.customerId != null) {
            customer = customerRepo.findById(req.customerId)
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
        }

        if (req.partnerId != null) {
            partner = partnerRepo.findById(req.partnerId)
                    .orElseThrow(() -> new RuntimeException("Partner not found"));
        }

        Contract c = new Contract();
        c.setContractNumber(req.contractNumber);
        c.setContractType(req.contractType);
        c.setCustomer(customer);
        c.setPartner(partner);
        c.setStartDate(req.startDate);
        c.setEndDate(req.endDate);
        c.setTotalValue(req.totalValue);
        c.setStatus(req.status);

        return mapToResponse(contractRepo.save(c));
    }

    // GET ALL
    public List<ContractResponse> getAll() {
        return contractRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // GET BY ID
    public ContractResponse getById(Integer id) {
        Contract c = contractRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Contract not found"));
        return mapToResponse(c);
    }

    // DELETE
    public void delete(Integer id) {
        contractRepo.deleteById(id);
    }

    // MAPPER
    private ContractResponse mapToResponse(Contract c) {
        ContractResponse res = new ContractResponse();

        res.id = c.getId();
        res.contractNumber = c.getContractNumber();
        res.contractType = c.getContractType();
        res.startDate = c.getStartDate();
        res.endDate = c.getEndDate();
        res.totalValue = c.getTotalValue();
        res.status = c.getStatus();

        res.customerName = c.getCustomer() != null ? c.getCustomer().getName() : null;
        res.partnerName = c.getPartner() != null ? c.getPartner().getName() : null;

        return res;
    }
}