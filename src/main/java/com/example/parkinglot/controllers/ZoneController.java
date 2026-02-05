package com.example.parkinglot.controllers;

import com.example.parkinglot.dtos.*;
import com.example.parkinglot.services.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Timer;

@RestController
@RequestMapping("api/v1/zones")
public class ZoneController {
    private final ZoneService zoneService;

    @Autowired
    public ZoneController(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ZoneResponse>> createVehicle(
            @RequestBody ZoneCreateRequest req
    ) {
        try {
            return ResponseEntity
                .status(201)
                .body(ApiResponse.success("Vehicle created successfully", zoneService.createZone(req)));
        } catch (Exception e) {
            return ResponseEntity
                .status(400)
                .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<List<ZoneSummaryResponse>>> getZoneStats() {
        try {
            long start = System.nanoTime();

            ResponseEntity r = ResponseEntity
                    .ok()
                    .body(ApiResponse.success("Zone summaries fetched successfully", zoneService.getAllZoneSummariesV2()));
            long durationNs = System.nanoTime() - start;
            System.out.println("Zone stats v1 fetched in " + durationNs + " ns");
            return r;
        } catch (Exception e) {
            return ResponseEntity
                .status(400)
                .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ZoneResponse>>> getPagedZones(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) Sort.Direction direction,
            @RequestParam(required = false) String keyword
    ) {
        try {
            return ResponseEntity
                .ok()
                .body(ApiResponse.success("Vehicles fetched successfully", zoneService.getPagedZone(page, size, sortBy, direction, keyword)));
        } catch (Exception e) {
            return ResponseEntity
                .status(400)
                .body(ApiResponse.error(e.getMessage()));
        }
    }
}
