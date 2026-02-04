package com.example.parkinglot.services;

import com.example.parkinglot.dtos.VehicleCreateRequest;
import com.example.parkinglot.dtos.VehicleResponse;
import com.example.parkinglot.models.Vehicle;
import com.example.parkinglot.repos.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {
    private final VehicleRepository repo;

    @Autowired
    public VehicleService(VehicleRepository repo) {
        this.repo = repo;
    }

    public VehicleResponse createVehicle(VehicleCreateRequest req) {
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate(req.getLicensePlate());
        vehicle.setColor(req.getColor());
        vehicle.setVehicleType(req.getVehicleType());
        return new VehicleResponse(repo.save(vehicle));
    }
}
