package com.example.parkinglot.controllers;

import com.example.parkinglot.dtos.ApiResponse;
import com.example.parkinglot.dtos.PageResponse;
import com.example.parkinglot.dtos.VehicleCreateRequest;
import com.example.parkinglot.dtos.VehicleResponse;
import com.example.parkinglot.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<VehicleResponse>> createVehicle(
            @RequestBody VehicleCreateRequest req
    ) {
        try {
            return ResponseEntity
                .status(201)
                .body(ApiResponse.success("Vehicle created successfully", vehicleService.createVehicle(req)));
        } catch (Exception e) {
            return ResponseEntity
                .status(400)
                .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<VehicleResponse>>> getPagedVehicles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) Sort.Direction direction,
            @RequestParam(required = false) String keyword
    ) {
        try {
            return ResponseEntity
                .ok()
                .body(ApiResponse.success("Vehicles fetched successfully", vehicleService.getPagedVehicles(page, size, sortBy, direction, keyword)));
        } catch (Exception e) {
            return ResponseEntity
                .status(400)
                .body(ApiResponse.error(e.getMessage()));
        }
    }
}
