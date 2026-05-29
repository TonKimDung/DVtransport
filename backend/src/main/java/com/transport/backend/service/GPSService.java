package com.transport.backend.service;

import com.transport.backend.dto.AlertDTO;
import com.transport.backend.dto.DriverWorkLogDTO;
import com.transport.backend.dto.GpsDTO;
import com.transport.backend.dto.GpsHistoryDTO;
import com.transport.backend.entity.DriverWorkLog;
import com.transport.backend.entity.GpsTracking;
import com.transport.backend.entity.Trip;
import com.transport.backend.repository.DriverWorkLogRepository;
import com.transport.backend.repository.GPSTrackingRepository;
import com.transport.backend.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GPSService {

        private final GPSTrackingRepository gpsRepo;
        private final TripRepository tripRepo;
        private final SimpMessagingTemplate messagingTemplate;
        private final DriverWorkLogRepository driverWorkLogRepo;
        private static final BigDecimal MAX_HOURS = BigDecimal.valueOf(8);

        private static final BigDecimal DANGER_HOURS = BigDecimal.valueOf(10);

        // DRIVER PUSH GPS
        public GpsDTO pushGPS(Integer tripId, Double lat, Double lng) {

                Trip trip = tripRepo.findById(tripId)
                                .orElseThrow(() -> new RuntimeException("Trip not found"));

                double lastDistance = 0.0;

                // GPS gần nhất
                GpsTracking lastGps = gpsRepo.findTopByTripIdOrderByRecordedAtDesc(tripId);

                // =========================
                // TÍNH KHOẢNG CÁCH
                // =========================
                if (lastGps != null) {

                        lastDistance = calculateDistance(
                                        lastGps.getLatitude().doubleValue(),
                                        lastGps.getLongitude().doubleValue(),
                                        lat,
                                        lng);

                        Double currentKm = trip.getCurrentDistanceKm();

                        if (currentKm == null) {
                                currentKm = 0.0;
                        }

                        double total = currentKm + lastDistance;

                        trip.setCurrentDistanceKm(total);

                        // =========================
                        // CẢNH BÁO SAI TUYẾN
                        // =========================
                        if (trip.getMaxDistanceKm() != null
                                        && total > trip.getMaxDistanceKm()
                                        && !Boolean.TRUE.equals(trip.getRouteWarning())) {

                                trip.setRouteWarning(true);

                                AlertDTO alert = new AlertDTO(
                                                "ROUTE_WARNING",
                                                tripId,
                                                "Xe đã vượt quá số km cho phép",
                                                LocalDateTime.now());

                                messagingTemplate.convertAndSend(
                                                "/topic/alerts",
                                                alert);
                        }
                }

                // =========================
                // CẢNH BÁO GIAO HÀNG TRỄ
                // =========================
                if (trip.getEstimatedArrival() != null
                                && LocalDateTime.now().isAfter(trip.getEstimatedArrival())
                                && !Boolean.TRUE.equals(trip.getDelayWarning())) {

                        trip.setDelayWarning(true);

                        AlertDTO alert = new AlertDTO(
                                        "DELAY_WARNING",
                                        tripId,
                                        "Chuyến xe giao hàng bị trễ",
                                        LocalDateTime.now());

                        messagingTemplate.convertAndSend(
                                        "/topic/alerts",
                                        alert);
                }

                // SAVE TRIP
                tripRepo.save(trip);

                checkDriverWorkingHours(trip);
                // =========================
                // SAVE GPS
                // =========================
                GpsTracking gps = new GpsTracking();

                gps.setTrip(trip);
                gps.setVehicle(trip.getVehicle());
                gps.setLatitude(BigDecimal.valueOf(lat));
                gps.setLongitude(BigDecimal.valueOf(lng));
                gps.setRecordedAt(LocalDateTime.now());

                gpsRepo.save(gps);

                // =========================
                // REALTIME DTO
                // =========================
                GpsDTO dto = new GpsDTO(
                                tripId,
                                trip.getVehicle().getId(),
                                lat,
                                lng,
                                LocalDateTime.now(),
                                lastDistance,
                                trip.getCurrentDistanceKm());

                // DEBUG
                System.out.println("GPS PUSH => " + dto);

                // REALTIME SEND
                messagingTemplate.convertAndSend(
                                "/topic/gps/" + tripId,
                                dto);

                return dto;
        }

        // =========================
        // HISTORY
        // =========================
        public List<GpsHistoryDTO> history(Integer tripId) {

                return gpsRepo.findByTripIdOrderByRecordedAtAsc(tripId)
                                .stream()
                                .map(g -> new GpsHistoryDTO(
                                                g.getId(),
                                                g.getTrip().getId(),
                                                g.getVehicle().getId(),
                                                g.getLatitude().doubleValue(),
                                                g.getLongitude().doubleValue(),
                                                g.getRecordedAt()))
                                .toList();
        }

        // =========================
        // DISTANCE CALCULATE
        // =========================
        private double calculateDistance(
                        double lat1,
                        double lon1,
                        double lat2,
                        double lon2) {

                final int R = 6371;

                double latDistance = Math.toRadians(lat2 - lat1);
                double lonDistance = Math.toRadians(lon2 - lon1);

                double a = Math.sin(latDistance / 2)
                                * Math.sin(latDistance / 2)
                                + Math.cos(Math.toRadians(lat1))
                                                * Math.cos(Math.toRadians(lat2))
                                                * Math.sin(lonDistance / 2)
                                                * Math.sin(lonDistance / 2);

                double c = 2 * Math.atan2(
                                Math.sqrt(a),
                                Math.sqrt(1 - a));

                return R * c;
        }

        private void checkDriverWorkingHours(Trip trip) {

                if (trip.getDriver() == null
                                || trip.getDepartureTime() == null
                                || !"CREATED".equals(trip.getStatus())) {

                        return;
                }

                // ====================================
                // TÍNH GIỜ LÁI XE
                // ====================================

                long minutes = Duration.between(
                                trip.getDepartureTime(),
                                LocalDateTime.now())
                                .toMinutes();

                BigDecimal currentHours = BigDecimal
                                .valueOf(minutes)
                                .divide(
                                                BigDecimal.valueOf(60),
                                                2,
                                                RoundingMode.HALF_UP);

                // ====================================
                // TỔNG GIỜ HÔM NAY
                // ====================================

                List<DriverWorkLog> todayLogs = driverWorkLogRepo.findByDriverIdAndWorkDate(
                                trip.getDriver().getId(),
                                LocalDate.now());

                BigDecimal totalToday = todayLogs.stream()
                                .map(log -> log.getDrivingHours() != null
                                                ? log.getDrivingHours()
                                                : BigDecimal.ZERO)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                BigDecimal finalHours = totalToday.add(currentHours);

                // ====================================
                // WARNING
                // ====================================

                boolean overtime = false;

                String warningLevel = "NORMAL";

                String warningMessage = null;

                if (finalHours.compareTo(DANGER_HOURS) > 0) {

                        overtime = true;

                        warningLevel = "DANGEROUS";

                        warningMessage = "Tài xế vượt quá 10 giờ lái xe";

                } else if (finalHours.compareTo(MAX_HOURS) > 0) {

                        overtime = true;

                        warningLevel = "WARNING";

                        warningMessage = "Tài xế vượt quá 8 giờ lái xe";
                }

                // ====================================
                // CHECK EXIST LOG
                // ====================================

                DriverWorkLog log = driverWorkLogRepo
                                .findByDriverIdAndTripId(
                                                trip.getDriver().getId(),
                                                trip.getId())
                                .orElse(null);

                if (log == null) {

                        log = DriverWorkLog.builder()
                                        .driver(trip.getDriver())
                                        .trip(trip)
                                        .workDate(LocalDate.now())
                                        .tripCount(1)
                                        .build();
                }

                // UPDATE REALTIME
                log.setDrivingHours(currentHours);

                log.setOvertime(overtime);

                log.setWarningLevel(warningLevel);

                log.setWarningMessage(warningMessage);

                driverWorkLogRepo.save(log);

                // ====================================
                // REALTIME ADMIN
                // ====================================

                DriverWorkLogDTO dto = new DriverWorkLogDTO(
                                log.getId(),
                                log.getDriver().getId(),
                                log.getTrip().getId(),
                                log.getWorkDate(),
                                log.getDrivingHours(),
                                log.getOvertime(),
                                log.getWarningLevel(),
                                log.getWarningMessage());

                messagingTemplate.convertAndSend("/topic/work-log", dto);

                // ====================================
                // REALTIME DRIVER
                // ====================================

                if (overtime) {

                        AlertDTO alert = new AlertDTO(
                                        "WORKING_HOURS_WARNING",
                                        trip.getId(),
                                        warningMessage,
                                        LocalDateTime.now());

                        messagingTemplate.convertAndSend(
                                        "/topic/driver-warning/"
                                                        + trip.getDriver().getId(),
                                        alert);

                        messagingTemplate.convertAndSend(
                                        "/topic/alerts",
                                        alert);
                }
        }
}