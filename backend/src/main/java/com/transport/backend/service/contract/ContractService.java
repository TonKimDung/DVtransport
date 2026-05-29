package com.transport.backend.service.contract;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.transport.backend.dto.contract.*;
import com.transport.backend.entity.*;
import com.transport.backend.repository.*;
import com.transport.backend.repository.contract.*;

@Service
public class ContractService {

    private final ContractRepository contractRepo;
    private final CustomerRepository customerRepo;
    private final PartnerRepository partnerRepo;

    private final DriverRepository driverRepo;
    private final DriverLicenseRepository driverLicenseRepo;

    public ContractService(
            ContractRepository contractRepo,
            CustomerRepository customerRepo,
            PartnerRepository partnerRepo,
            DriverRepository driverRepo,
            DriverLicenseRepository driverLicenseRepo) {

        this.contractRepo = contractRepo;
        this.customerRepo = customerRepo;
        this.partnerRepo = partnerRepo;
        this.driverRepo = driverRepo;
        this.driverLicenseRepo = driverLicenseRepo;
    }

    // CREATE
    public ContractResponse create(ContractRequest req) {

        Customer customer = null;
        Partner partner = null;
        Driver driver = null;
        DriverLicense driverLicense = null;

        // CUSTOMER
        if (req.getCustomerId() != null) {

            customer = customerRepo.findById(req.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
        }

        // PARTNER
        if (req.getPartnerId() != null) {

            partner = partnerRepo.findById(req.getPartnerId())
                    .orElseThrow(() -> new RuntimeException("Partner not found"));
        }

        // DRIVER
        if (req.getDriverId() != null) {

            driver = driverRepo.findById(req.getDriverId())
                    .orElseThrow(() -> new RuntimeException("Driver not found"));
        }

        // DRIVER LICENSE
        if (req.getDriverLicenseId() != null) {

            driverLicense = driverLicenseRepo
                    .findById(req.getDriverLicenseId())
                    .orElseThrow(() -> new RuntimeException("Driver license not found"));
        }

        Contract c = new Contract();

        c.setContractNumber(req.getContractNumber());
        c.setContractType(req.getContractType());

        c.setCustomer(customer);
        c.setPartner(partner);

        // DRIVER
        c.setDriver(driver);

        // LICENSE
        c.setDriverLicense(driverLicense);

        c.setStartDate(req.getStartDate());
        c.setEndDate(req.getEndDate());

        c.setTotalValue(req.getTotalValue());

        // salary
        c.setBaseSalary(req.getBaseSalary());

        c.setStatus(req.getStatus());

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
        res.baseSalary = c.getBaseSalary();

        res.status = c.getStatus();

        // CUSTOMER
        res.customerName = c.getCustomer() != null
                ? c.getCustomer().getName()
                : null;

        // PARTNER
        res.partnerName = c.getPartner() != null
                ? c.getPartner().getName()
                : null;

        // DRIVER
        res.driverName = c.getDriver() != null
                ? c.getDriver().getFullName()
                : null;

        // LICENSE
        res.licenseNumber = c.getDriverLicense() != null
                ? c.getDriverLicense().getLicenseNumber()
                : null;

        return res;
    }
}