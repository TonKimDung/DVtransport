package com.transport.backend.repository.contract;

import org.springframework.data.jpa.repository.JpaRepository;
import com.transport.backend.entity.Partner;

public interface PartnerRepository extends JpaRepository<Partner, Integer> {
}