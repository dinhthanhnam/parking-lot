package com.example.parkinglot.controllers;

import com.example.parkinglot.dtos.*;
import com.example.parkinglot.services.TicketService;
import com.example.parkinglot.services.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/tickets")
public class ParkingTicketController {
    private final TicketService ticketService;

    @Autowired
    public ParkingTicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/check-in")
    public ResponseEntity<ApiResponse<TicketResponse>> checkInVehicle(
            @RequestBody TicketRequest req
    ) {
        try {
            return ResponseEntity
                .status(201)
                .body(ApiResponse.success("Checked in successfully", ticketService.checkIn(req)));
        } catch (Exception e) {
            return ResponseEntity
                .status(400)
                .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/check-out/{vehicleId}")
    public ResponseEntity<ApiResponse<TicketResponse>> checkOutVehicle(
            @PathVariable Long vehicleId
    ) {
        try {
            return ResponseEntity
                    .status(201)
                    .body(ApiResponse.success("Checked out successfully", ticketService.checkOut(vehicleId)));
        } catch (Exception e) {
            return ResponseEntity
                    .status(400)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<List<TicketSummaryResponse>>> getTicketsSummaries()
    {
        try {
            return ResponseEntity
                .ok()
                .body(ApiResponse.success("Vehicles fetched successfully", ticketService.getTicketsSummary()));
        } catch (Exception e) {
            return ResponseEntity
                .status(400)
                .body(ApiResponse.error(e.getMessage()));
        }
    }
}
