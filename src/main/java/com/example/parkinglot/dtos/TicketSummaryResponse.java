package com.example.parkinglot.dtos;

import com.example.parkinglot.models.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketSummaryResponse {
    Long id;
    String licensePlate;
    VehicleType vehicleType;
    String zoneName;
}
