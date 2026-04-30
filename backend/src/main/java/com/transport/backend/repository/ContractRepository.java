package com.transport.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transport.backend.entity.Contract;

public interface ContractRepository extends JpaRepository<Contract, Integer> {
}