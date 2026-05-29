package com.transport.backend.dto.driver_work_log;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverWorkLogResponse {

    private Integer id;

    private Integer driverId;

    private String driverName;

    private Integer tripId;

    private String tripCode;

    private LocalDate workDate;

    private BigDecimal drivingHours;

    private Integer tripCount;

    private Boolean overtime;

    private String warningLevel;

    private String warningMessage;
}