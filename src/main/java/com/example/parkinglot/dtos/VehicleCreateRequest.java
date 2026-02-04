package com.example.parkinglot.dtos;

import com.example.parkinglot.models.VehicleType;
import lombok.Data;

@Data
public class VehicleCreateRequest {
    String licensePlate;
    String color;
    VehicleType vehicleType;
}
