package com.transport.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transport.backend.entity.Partner;

public interface PartnerRepository extends JpaRepository<Partner, Integer> {
}