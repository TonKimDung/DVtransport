package com.transport.backend.repository.contract;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.transport.backend.entity.Partner;

public interface PartnerRepository extends JpaRepository<Partner, Integer> {
    @Query("""
                SELECT p
                FROM Partner p
                WHERE p.id NOT IN (
                    SELECT c.partner.id
                    FROM Contract c
                    WHERE c.partner IS NOT NULL
                      AND c.status = 'ACTIVE'
                )
            """)
    List<Partner> findPartnersWithoutContract();
}