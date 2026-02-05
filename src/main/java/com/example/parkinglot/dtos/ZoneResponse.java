package com.example.parkinglot.dtos;

import com.example.parkinglot.models.Zone;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZoneResponse {
    Long id;
    String name;
    Integer capacity;
    Integer occupiedSpots;

    public ZoneResponse(Zone zone) {
        this.id = zone.getId();
        this.name = zone.getName();
        this.capacity = zone.getCapacity();
        this.occupiedSpots = zone.getOccupiedSpots();
    }
}
