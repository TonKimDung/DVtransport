package com.transport.backend.controller;

import com.transport.backend.dto.GpsDTO;
import com.transport.backend.dto.GpsHistoryDTO;
import com.transport.backend.dto.GpsRequest;
import com.transport.backend.service.GPSService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gps")
@RequiredArgsConstructor
public class GPSController {

    private final GPSService gpsService;

    // DRIVER PUSH GPS
    @PostMapping("/push")
    public GpsDTO push(@RequestBody GpsRequest request) {

        return gpsService.pushGPS(
                request.getTripId(),
                request.getLat(),
                request.getLng());
    }

    // HISTORY
    @GetMapping("/history/{tripId}")
    public List<GpsHistoryDTO> history(
            @PathVariable Integer tripId) {

        return gpsService.history(tripId);
    }
}