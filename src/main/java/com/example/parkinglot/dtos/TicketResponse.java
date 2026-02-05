package com.example.parkinglot.dtos;

import com.example.parkinglot.models.ParkingTicket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponse {
    Long id;
    LocalDateTime checkInTime;
    LocalDateTime checkOutTime;
    String zoneName;
    String licensePlate;
    String vehicleType;

    public TicketResponse(ParkingTicket pt) {
        this.id = pt.getId();
        this.checkInTime = pt.getCheckInTime();
        this.checkOutTime = pt.getCheckOutTime();
        this.zoneName = pt.getZone().getName();
        this.licensePlate = pt.getVehicle().getLicensePlate();
        this.vehicleType = pt.getVehicle().getVehicleType().name();
    }
}
