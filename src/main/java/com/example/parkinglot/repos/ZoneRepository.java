package com.example.parkinglot.repos;

import com.example.parkinglot.dtos.ZoneResponse;
import com.example.parkinglot.dtos.ZoneSummaryResponse;
import com.example.parkinglot.models.Zone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ZoneRepository extends JpaRepository<Zone, Long> {
    @Query("""
            SELECT new com.example.parkinglot.dtos.ZoneResponse(
                z.id,
                z.name,
                z.capacity,
                z.occupiedSpots
            )
            FROM Zone z
            WHERE :keyword IS NULL OR LOWER(z.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """)
    Page<ZoneResponse> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);
//    @Query("""
//            SELECT (z.occupiedSpots < z.capacity)
//            FROM Zone z
//            WHERE z.id = :zoneId
//            """)
//    Boolean isAvailableById (Long zoneId);

    @Query("""
            SELECT new com.example.parkinglot.dtos.ZoneSummaryResponse(
                z.id,
                z.name,
                z.capacity,
                z.occupiedSpots,
                (z.capacity - COUNT(pt))
            )
            FROM Zone z
            LEFT JOIN ParkingTicket pt ON pt.zone = z AND pt.checkOutTime IS NULL
            GROUP BY z.id, z.name, z.capacity, z.occupiedSpots
            """)
    List<ZoneSummaryResponse> findAllSummaries();
}
