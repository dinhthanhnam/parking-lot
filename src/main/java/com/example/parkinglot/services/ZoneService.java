package com.example.parkinglot.services;

import com.example.parkinglot.dtos.*;
import com.example.parkinglot.models.Zone;
import com.example.parkinglot.repos.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZoneService {
    private final ZoneRepository repo;

    @Autowired
    public ZoneService(ZoneRepository repo) {
        this.repo = repo;
    }

    public ZoneResponse createZone(ZoneCreateRequest req) {
        Zone zone = new Zone();
        zone.setName(req.getName());
        zone.setCapacity(req.getCapacity());
        zone.setOccupiedSpots(0);
        return new ZoneResponse(repo.save(zone));
    }

    public PageResponse<ZoneResponse> getPagedZone(Integer page, Integer size, String sortBy, Sort.Direction direction, String keyword) {
        Sort sort = Sort.unsorted();
        if (sortBy != null && direction != null) {
            sort = Sort.by(direction, sortBy);
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ZoneResponse> zonePage = repo.findAllByKeyword(keyword, pageable);

        return PageResponse.from(zonePage);
    }

    public List<ZoneSummaryResponse> getAllZoneSummariesV1() {
        List<Zone> zones = repo.findAll();
        return zones.stream().map(zone -> {
            Long availableSpots = Long.parseLong(String.valueOf(zone.getCapacity() - zone.getOccupiedSpots()));
            return new ZoneSummaryResponse(
                    zone.getId(),
                    zone.getName(),
                    zone.getCapacity(),
                    zone.getOccupiedSpots(),
                    availableSpots
            );
        }).toList();
    }
    public List<ZoneSummaryResponse> getAllZoneSummariesV2() {
        return repo.findAllSummaries();
    }
}
