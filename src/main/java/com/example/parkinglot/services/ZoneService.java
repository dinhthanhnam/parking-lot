package com.example.parkinglot.services;

import com.example.parkinglot.dtos.PageResponse;
import com.example.parkinglot.dtos.VehicleResponse;
import com.example.parkinglot.dtos.ZoneCreateRequest;
import com.example.parkinglot.dtos.ZoneResponse;
import com.example.parkinglot.models.Zone;
import com.example.parkinglot.repos.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    public PageResponse<ZoneResponse> getPagedVehicles(Integer page, Integer size, String sortBy, Sort.Direction direction, String keyword) {
        Sort sort = Sort.unsorted();
        if (sortBy != null && direction != null) {
            sort = Sort.by(direction, sortBy);
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ZoneResponse> zonePage = repo.findAllByKeyword(keyword, pageable);

        return PageResponse.from(zonePage);
    }
}
