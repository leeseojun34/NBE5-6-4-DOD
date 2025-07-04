package com.grepp.spring.app.model.location.rest;

import com.grepp.spring.app.model.location.model.LocationDTO;
import com.grepp.spring.app.model.location.service.LocationService;
import com.grepp.spring.app.model.middle_region.domain.MiddleRegion;
import com.grepp.spring.app.model.middle_region.repos.MiddleRegionRepository;
import com.grepp.spring.util.CustomCollectors;
import com.grepp.spring.util.ReferencedException;
import com.grepp.spring.util.ReferencedWarning;
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

    @GetMapping("/{id}")
    public ResponseEntity<LocationDTO> getLocation(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(locationService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createLocation(@RequestBody @Valid final LocationDTO locationDTO) {
        final Long createdId = locationService.create(locationDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateLocation(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final LocationDTO locationDTO) {
        locationService.update(id, locationDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteLocation(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = locationService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        locationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/middleRegionValues")
    public ResponseEntity<Map<Long, Long>> getMiddleRegionValues() {
        return ResponseEntity.ok(middleRegionRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(MiddleRegion::getId, MiddleRegion::getId)));
    }

}
