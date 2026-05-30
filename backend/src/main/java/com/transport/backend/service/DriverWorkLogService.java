package com.transport.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.transport.backend.dto.driver_work_log.DriverWorkLogResponse;
import com.transport.backend.entity.DriverWorkLog;
import com.transport.backend.repository.DriverWorkLogRepository;

@Service
public class DriverWorkLogService {

    private final DriverWorkLogRepository repo;

    public DriverWorkLogService(
            DriverWorkLogRepository repo) {

        this.repo = repo;
    }

    public List<DriverWorkLogResponse> getAll() {

        return repo.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    private DriverWorkLogResponse map(
            DriverWorkLog log) {

        DriverWorkLogResponse res = new DriverWorkLogResponse();

        res.setId(log.getId());

        if (log.getDriver() != null) {

            res.setDriverId(
                    log.getDriver().getId());

            res.setDriverName(
                    log.getDriver().getFullName());
        }

        if (log.getTrip() != null) {

            res.setTripId(
                    log.getTrip().getId());

            res.setTripCode(
                    log.getTrip().getTripCode());
        }

        res.setWorkDate(log.getWorkDate());

        res.setDrivingHours(
                log.getDrivingHours());

        res.setTripCount(
                log.getTripCount());

        res.setOvertime(
                log.getOvertime());

        res.setWarningLevel(
                log.getWarningLevel());

        res.setWarningMessage(
                log.getWarningMessage());

        return res;
    }
}