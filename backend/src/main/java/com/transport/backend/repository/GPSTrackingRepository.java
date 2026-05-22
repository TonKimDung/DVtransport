package com.transport.backend.repository;

import com.transport.backend.entity.GpsTracking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GPSTrackingRepository
        extends JpaRepository<GpsTracking, Integer> {

    List<GpsTracking> findByTripIdOrderByRecordedAtAsc(Integer tripId);

    GpsTracking findTopByTripIdOrderByRecordedAtDesc(Integer tripId);
}