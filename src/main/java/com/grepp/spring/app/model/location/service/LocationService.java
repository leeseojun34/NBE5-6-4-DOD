package com.grepp.spring.app.model.location.service;

import com.grepp.spring.app.model.location.domain.Location;
import com.grepp.spring.app.model.location.model.LocationDTO;
import com.grepp.spring.app.model.location.repos.LocationRepository;
import com.grepp.spring.app.model.middle_region.domain.MiddleRegion;
import com.grepp.spring.app.model.middle_region.repos.MiddleRegionRepository;
import com.grepp.spring.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final MiddleRegionRepository middleRegionRepository;

    public LocationService(final LocationRepository locationRepository,
            final MiddleRegionRepository middleRegionRepository) {
        this.locationRepository = locationRepository;
        this.middleRegionRepository = middleRegionRepository;
    }

    public List<LocationDTO> findAll() {
        final List<Location> locations = locationRepository.findAll(Sort.by("locationId"));
        return locations.stream()
                .map(location -> mapToDTO(location, new LocationDTO()))
                .toList();
    }

    public LocationDTO get(final Long locationId) {
        return locationRepository.findById(locationId)
                .map(location -> mapToDTO(location, new LocationDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final LocationDTO locationDTO) {
        final Location location = new Location();
        mapToEntity(locationDTO, location);
        return locationRepository.save(location).getLocationId();
    }

    public void update(final Long locationId, final LocationDTO locationDTO) {
        final Location location = locationRepository.findById(locationId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(locationDTO, location);
        locationRepository.save(location);
    }

    public void delete(final Long locationId) {
        locationRepository.deleteById(locationId);
    }

    private LocationDTO mapToDTO(final Location location, final LocationDTO locationDTO) {
        locationDTO.setLocationId(location.getLocationId());
        locationDTO.setLatitude(location.getLatitude());
        locationDTO.setLongitude(location.getLongitude());
        locationDTO.setLocationName(location.getLocationName());
        locationDTO.setMiddleRegion(location.getMiddleRegion() == null ? null : location.getMiddleRegion().getMiddleRegionId());
        return locationDTO;
    }

    private Location mapToEntity(final LocationDTO locationDTO, final Location location) {
        location.setLatitude(locationDTO.getLatitude());
        location.setLongitude(locationDTO.getLongitude());
        location.setLocationName(locationDTO.getLocationName());
        final MiddleRegion middleRegion = locationDTO.getMiddleRegion() == null ? null : middleRegionRepository.findById(locationDTO.getMiddleRegion())
                .orElseThrow(() -> new NotFoundException("middleRegion not found"));
        location.setMiddleRegion(middleRegion);
        return location;
    }

    public boolean middleRegionExists(final Long middleRegionId) {
        return locationRepository.existsByMiddleRegionMiddleRegionId(middleRegionId);
    }

}
