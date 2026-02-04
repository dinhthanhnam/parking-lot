package com.example.parkinglot.repos;

import com.example.parkinglot.models.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZoneRepository extends JpaRepository<Zone, Long> {
}
