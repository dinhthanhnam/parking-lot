package com.example.parkinglot.controllers;

import com.example.parkinglot.dtos.*;
import com.example.parkinglot.services.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ZoneResponse>>> getPagedVehicles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) Sort.Direction direction,
            @RequestParam(required = false) String keyword
    ) {
        try {
            return ResponseEntity
                .ok()
                .body(ApiResponse.success("Vehicles fetched successfully", zoneService.getPagedVehicles(page, size, sortBy, direction, keyword)));
        } catch (Exception e) {
            return ResponseEntity
                .status(400)
                .body(ApiResponse.error(e.getMessage()));
        }
    }
}
