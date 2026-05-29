package com.transport.backend.service.contract;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.transport.backend.dto.contract.PartnerRequest;
import com.transport.backend.dto.contract.PartnerResponse;
import com.transport.backend.entity.Partner;
import com.transport.backend.repository.contract.PartnerRepository;

@Service
public class PartnerService {

    private final PartnerRepository repo;

    public PartnerService(PartnerRepository repo) {
        this.repo = repo;
    }

    public PartnerResponse create(PartnerRequest req) {
        Partner p = new Partner();
        p.setName(req.getName());
        p.setStatus(req.getStatus());
        p.setPhone(req.getPhone());
        p.setEmail(req.getEmail());
        p.setAddress(req.getAddress());

        return mapToResponse(repo.save(p));
    }

    public List<PartnerResponse> getAvailable() {
        return repo.findPartnersWithoutContract()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<PartnerResponse> getAll() {
        return repo.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private PartnerResponse mapToResponse(Partner p) {
        PartnerResponse res = new PartnerResponse();
        res.setId(p.getId());
        res.setName(p.getName());
        res.setStatus(p.getStatus());
        res.setPhone(p.getPhone());
        res.setEmail(p.getEmail());
        res.setAddress(p.getAddress());
        res.setCreatedAt(p.getCreatedAt());
        return res;
    }
}