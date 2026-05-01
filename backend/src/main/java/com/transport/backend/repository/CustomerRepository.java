package com.transport.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transport.backend.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}