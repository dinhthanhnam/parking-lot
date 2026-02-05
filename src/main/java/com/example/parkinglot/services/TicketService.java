package com.example.parkinglot.services;

import com.example.parkinglot.dtos.PageResponse;
import com.example.parkinglot.dtos.TicketRequest;
import com.example.parkinglot.dtos.TicketResponse;
import com.example.parkinglot.dtos.TicketSummaryResponse;
import com.example.parkinglot.models.ParkingTicket;
import com.example.parkinglot.models.Vehicle;
import com.example.parkinglot.models.Zone;
import com.example.parkinglot.repos.ParkingTicketRepository;
import com.example.parkinglot.repos.VehicleRepository;
import com.example.parkinglot.repos.ZoneRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {
    private final ParkingTicketRepository repo;
    private final ZoneRepository zoneRepo;
    private final VehicleRepository vehicleRepo;

    @Autowired
    public TicketService(ParkingTicketRepository repo, ZoneRepository zoneRepo, VehicleRepository vehicleRepo) {
        this.repo = repo;
        this.vehicleRepo = vehicleRepo;
        this.zoneRepo = zoneRepo;
    }

    @Transactional
    public TicketResponse checkIn(TicketRequest req) {
        Zone zone = zoneRepo.findById(req.getZoneId()).orElseThrow(() -> new RuntimeException("Zone not found"));
        int occupiedSpots = zone.getOccupiedSpots();
        if (occupiedSpots >= zone.getCapacity()) {
            throw new RuntimeException("Zone is full");
        }
        Vehicle vehicle = vehicleRepo.findById(req.getVehicleId()).orElseThrow(() -> new RuntimeException("Vehicle not found"));
        boolean hasActiveTicket = repo.findActiveTicketByVehicleId(vehicle.getId()).isPresent();
        if (hasActiveTicket) {
            throw new RuntimeException("Vehicle already has an active ticket");
        }
        ParkingTicket pt = new ParkingTicket();
        pt.setZone(zone);
        pt.setVehicle(vehicle);
        pt.setCheckInTime(LocalDateTime.now());
        zone.setOccupiedSpots(occupiedSpots + 1);
        return new TicketResponse(repo.save(pt));
    }

    @Transactional
    public TicketResponse checkOut(Long vehicleId) {
        ParkingTicket pt = repo.findActiveTicketByVehicleId(vehicleId).orElseThrow(() -> new RuntimeException("Ticket not found"));
        pt.setCheckOutTime(LocalDateTime.now());
        Zone zone = pt.getZone();
        int occupiedSpots = zone.getOccupiedSpots();
        zone.setOccupiedSpots(occupiedSpots - 1);
        return new TicketResponse(repo.save(pt));
    }

    public List<TicketSummaryResponse> getTicketsSummary() {
        return repo.findAllTicketSummaries();
    }
}
