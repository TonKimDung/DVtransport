package com.transport.backend.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transport.backend.entity.Payroll;

public interface PayrollRepository extends JpaRepository<Payroll, Integer> {

    // Lấy lương theo tháng/năm (dùng cho report)
    List<Payroll> findByMonthAndYear(Integer month, Integer year);

    // Lấy lương theo tài xế
    List<Payroll> findByDriverId(Integer driverId);
    Optional<Payroll> findByDriverIdAndMonthAndYear(Integer driverId, Integer month, Integer year);
}