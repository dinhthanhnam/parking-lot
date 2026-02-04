package com.example.parkinglot.dtos;

import com.example.parkinglot.models.Vehicle;
import com.example.parkinglot.models.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VehicleResponse {
    Long id;
    String licensePlate;
    String color;
    VehicleType vehicleType;

    public VehicleResponse(Vehicle vehicle) {
        this.id = vehicle.getId();
        this.licensePlate = vehicle.getLicensePlate();
        this.color = vehicle.getColor();
        this.vehicleType = vehicle.getVehicleType();
    }
}
