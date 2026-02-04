package com.example.parkinglot.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "license_plate", nullable = false, unique = true)
    String licensePlate;

    String color;

    @Enumerated(EnumType.STRING)
    VehicleType vehicleType;

    @ManyToOne
    @JoinColumn(name = "zone_id")
    Zone zone;

}
