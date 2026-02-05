package com.example.parkinglot.dtos;

import lombok.Data;

@Data
public class ZoneCreateRequest {
    String name;
    Integer capacity;
}
