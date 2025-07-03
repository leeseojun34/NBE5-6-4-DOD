package com.grepp.spring.location_candidate.rest;

import com.grepp.spring.detail.domain.Detail;
import com.grepp.spring.detail.repos.DetailRepository;
import com.grepp.spring.location_candidate.model.LocationCandidateDTO;
import com.grepp.spring.location_candidate.service.LocationCandidateService;
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
@RequestMapping(value = "/api/locationCandidates", produces = MediaType.APPLICATION_JSON_VALUE)
public class LocationCandidateResource {

    private final LocationCandidateService locationCandidateService;
    private final DetailRepository detailRepository;

    public LocationCandidateResource(final LocationCandidateService locationCandidateService,
            final DetailRepository detailRepository) {
        this.locationCandidateService = locationCandidateService;
        this.detailRepository = detailRepository;
    }

    @GetMapping
    public ResponseEntity<List<LocationCandidateDTO>> getAllLocationCandidates() {
        return ResponseEntity.ok(locationCandidateService.findAll());
    }

    @GetMapping("/{locationCandidateId}")
    public ResponseEntity<LocationCandidateDTO> getLocationCandidate(
            @PathVariable(name = "locationCandidateId") final Long locationCandidateId) {
        return ResponseEntity.ok(locationCandidateService.get(locationCandidateId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createLocationCandidate(
            @RequestBody @Valid final LocationCandidateDTO locationCandidateDTO) {
        final Long createdLocationCandidateId = locationCandidateService.create(locationCandidateDTO);
        return new ResponseEntity<>(createdLocationCandidateId, HttpStatus.CREATED);
    }

    @PutMapping("/{locationCandidateId}")
    public ResponseEntity<Long> updateLocationCandidate(
            @PathVariable(name = "locationCandidateId") final Long locationCandidateId,
            @RequestBody @Valid final LocationCandidateDTO locationCandidateDTO) {
        locationCandidateService.update(locationCandidateId, locationCandidateDTO);
        return ResponseEntity.ok(locationCandidateId);
    }

    @DeleteMapping("/{locationCandidateId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteLocationCandidate(
            @PathVariable(name = "locationCandidateId") final Long locationCandidateId) {
        final ReferencedWarning referencedWarning = locationCandidateService.getReferencedWarning(locationCandidateId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        locationCandidateService.delete(locationCandidateId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/detailValues")
    public ResponseEntity<Map<Long, String>> getDetailValues() {
        return ResponseEntity.ok(detailRepository.findAll(Sort.by("detailId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Detail::getDetailId, Detail::getLocation)));
    }

}
