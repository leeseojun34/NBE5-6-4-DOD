package com.grepp.spring.location.rest;

import com.grepp.spring.location.model.LocationDTO;
import com.grepp.spring.location.service.LocationService;
import com.grepp.spring.middle_region.domain.MiddleRegion;
import com.grepp.spring.middle_region.repos.MiddleRegionRepository;
import com.grepp.spring.util.CustomCollectors;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/locations", produces = MediaType.APPLICATION_JSON_VALUE)
public class LocationResource {

    private final LocationService locationService;
    private final MiddleRegionRepository middleRegionRepository;

    public LocationResource(final LocationService locationService,
            final MiddleRegionRepository middleRegionRepository) {
        this.locationService = locationService;
        this.middleRegionRepository = middleRegionRepository;
    }

    @GetMapping
    public ResponseEntity<List<LocationDTO>> getAllLocations() {
        return ResponseEntity.ok(locationService.findAll());
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<LocationDTO> getLocation(
            @PathVariable(name = "locationId") final Long locationId) {
        return ResponseEntity.ok(locationService.get(locationId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createLocation(@RequestBody @Valid final LocationDTO locationDTO) {
        final Long createdLocationId = locationService.create(locationDTO);
        return new ResponseEntity<>(createdLocationId, HttpStatus.CREATED);
    }

    @PutMapping("/{locationId}")
    public ResponseEntity<Long> updateLocation(
            @PathVariable(name = "locationId") final Long locationId,
            @RequestBody @Valid final LocationDTO locationDTO) {
        locationService.update(locationId, locationDTO);
        return ResponseEntity.ok(locationId);
    }

    @DeleteMapping("/{locationId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteLocation(
            @PathVariable(name = "locationId") final Long locationId) {
        locationService.delete(locationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/middleRegionValues")
    public ResponseEntity<Map<Long, Long>> getMiddleRegionValues() {
        return ResponseEntity.ok(middleRegionRepository.findAll(Sort.by("middleRegionId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(MiddleRegion::getMiddleRegionId, MiddleRegion::getMiddleRegionId)));
    }

}
