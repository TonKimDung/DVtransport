package com.transport.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.transport.backend.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query("""
                SELECT d
                FROM Customer d
                WHERE d.id NOT IN (
                    SELECT c.customer.id
                    FROM Contract c
                    WHERE c.customer IS NOT NULL
                      AND c.status = 'ACTIVE'
                )
            """)
    List<Customer> findCustomersWithoutContract();
}