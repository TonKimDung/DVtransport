package com.transport.backend.controller;

import com.transport.backend.dto.GpsDTO;
import com.transport.backend.entity.GpsTracking;
import com.transport.backend.service.GPSService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gps")
public class GPSController {

    private final GPSService gpsService;

    public GPSController(GPSService gpsService) {
        this.gpsService = gpsService;
    }

    // 📍 gửi GPS
    @PostMapping
    public GpsDTO sendGPS(
            @RequestParam Integer tripId,
            @RequestParam Double lat,
            @RequestParam Double lng) {
        return gpsService.saveGPS(tripId, lat, lng);
    }

    // 📊 lịch sử
    @GetMapping("/history/{tripId}")
    public List<GpsTracking> history(@PathVariable Integer tripId) {
        return gpsService.getHistory(tripId);
    }
}