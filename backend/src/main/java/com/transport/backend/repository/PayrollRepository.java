package com.transport.backend.repository;

import com.transport.backend.entity.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayrollRepository extends JpaRepository<Payroll, Integer> {

    // Lấy lương theo tháng/năm (dùng cho report)
    List<Payroll> findByMonthAndYear(Integer month, Integer year);

    // Lấy lương theo tài xế
    List<Payroll> findByDriverId(Integer driverId);
}