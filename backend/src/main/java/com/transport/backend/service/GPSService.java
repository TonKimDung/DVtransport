package com.transport.backend.service;

import com.transport.backend.dto.AlertDTO;
import com.transport.backend.dto.GpsDTO;
import com.transport.backend.dto.GpsHistoryDTO;
import com.transport.backend.entity.GpsTracking;
import com.transport.backend.entity.Trip;
import com.transport.backend.repository.GPSTrackingRepository;
import com.transport.backend.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GPSService {

    private final GPSTrackingRepository gpsRepo;
    private final TripRepository tripRepo;
    private final SimpMessagingTemplate messagingTemplate;

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
}