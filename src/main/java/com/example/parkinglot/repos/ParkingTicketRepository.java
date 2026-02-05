package com.example.parkinglot.repos;

import com.example.parkinglot.dtos.TicketSummaryResponse;
import com.example.parkinglot.models.ParkingTicket;
import com.example.parkinglot.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingTicketRepository extends JpaRepository<ParkingTicket, Long> {
    @Query("""
        SELECT new com.example.parkinglot.dtos.TicketSummaryResponse(
            pt.id,
            pt.vehicle.licensePlate,
            pt.vehicle.vehicleType,
            pt.zone.name AS zoneName
        ) FROM ParkingTicket pt
        WHERE pt.checkInTime >= CURRENT_DATE
            AND pt.checkInTime < CURRENT_TIMESTAMP
        """)
    List<TicketSummaryResponse> findAllTicketSummaries();

    @Query("""
            SELECT pt
            FROM ParkingTicket pt
            WHERE pt.vehicle.id = :vehicleId
            AND pt.checkOutTime IS NULL
            ORDER BY pt.checkInTime DESC
            LIMIT 1
            """)
    Optional<ParkingTicket> findActiveTicketByVehicleId(@Param("vehicleId") Long vehicleId);
}
