package com.transport.backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transport.backend.entity.DriverWorkLog;

public interface DriverWorkLogRepository extends JpaRepository<DriverWorkLog, Integer> {
    boolean existsByDriverIdAndTripId(Integer driverId, Integer tripId);
    List<DriverWorkLog> findByDriver_IdAndWorkDate(Integer driverId, LocalDate workDate);
    List<DriverWorkLog> findByDriverIdAndWorkDateBetween(
            Integer driverId,
            LocalDate startDate,
            LocalDate endDate
    );
}