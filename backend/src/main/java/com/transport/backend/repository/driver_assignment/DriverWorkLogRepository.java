package com.transport.backend.repository.driver_assignment;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transport.backend.entity.DriverWorkLog;

public interface DriverWorkLogRepository
        extends JpaRepository<DriverWorkLog, Integer> {

    List<DriverWorkLog> findByDriver_IdAndWorkDate(
            Integer driverId, LocalDate workDate);
}