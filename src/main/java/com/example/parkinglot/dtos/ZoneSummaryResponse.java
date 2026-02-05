package com.example.parkinglot.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZoneSummaryResponse {
    Long id;
    String name;
    Integer capacity;
    Integer occupiedSpots;
    Long availableSpots;
}
