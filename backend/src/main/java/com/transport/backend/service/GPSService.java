package com.transport.backend.service;

import com.transport.backend.dto.GpsDTO;
import com.transport.backend.entity.GpsTracking;
import com.transport.backend.entity.Trip;
import com.transport.backend.repository.GPSTrackingRepository;
import com.transport.backend.repository.TripRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class GPSService {

    private final GPSTrackingRepository gpsRepo;
    private final TripRepository tripRepo;
    private final SimpMessagingTemplate messagingTemplate;

    public GPSService(GPSTrackingRepository gpsRepo,
            TripRepository tripRepo,
            SimpMessagingTemplate messagingTemplate) {
        this.gpsRepo = gpsRepo;
        this.tripRepo = tripRepo;
        this.messagingTemplate = messagingTemplate;
    }

    // 📍 nhận GPS từ xe
    public GpsDTO saveGPS(Integer tripId, Double lat, Double lng) {

        Trip trip = tripRepo.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        GpsTracking gps = new GpsTracking();
        gps.setTrip(trip);
        gps.setVehicle(trip.getVehicle());
        gps.setLatitude(BigDecimal.valueOf(lat));
        gps.setLongitude(BigDecimal.valueOf(lng));
        gps.setRecordedAt(LocalDateTime.now());

        gpsRepo.save(gps);

        // 🔥 realtime push
        GpsDTO dto = new GpsDTO(
                gps.getVehicle().getId(),
                gps.getLatitude().doubleValue(),
                gps.getLongitude().doubleValue(),
                gps.getRecordedAt());

        messagingTemplate.convertAndSend("/topic/gps", dto);
        return dto;
    }

    // 📊 lịch sử
    public List<GpsTracking> getHistory(Integer tripId) {
        return gpsRepo.findByTripIdOrderByRecordedAtAsc(tripId);
    }
}