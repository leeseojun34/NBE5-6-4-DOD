package com.grepp.spring.app.controller.api;

import com.grepp.spring.app.model.depart_region.model.DepartRegionDTO;
import com.grepp.spring.app.model.depart_region.service.DepartRegionService;
import com.grepp.spring.app.model.meeting.domain.Meeting;
import com.grepp.spring.app.model.meeting.repos.MeetingRepository;
import com.grepp.spring.app.model.middle_region.domain.MiddleRegion;
import com.grepp.spring.app.model.middle_region.repos.MiddleRegionRepository;
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
@RequestMapping(value = "/api/departRegions", produces = MediaType.APPLICATION_JSON_VALUE)
public class DepartRegionResource {

    private final DepartRegionService departRegionService;
    private final MeetingRepository meetingRepository;
    private final MiddleRegionRepository middleRegionRepository;

    public DepartRegionResource(final DepartRegionService departRegionService,
            final MeetingRepository meetingRepository,
            final MiddleRegionRepository middleRegionRepository) {
        this.departRegionService = departRegionService;
        this.meetingRepository = meetingRepository;
        this.middleRegionRepository = middleRegionRepository;
    }

    @GetMapping
    public ResponseEntity<List<DepartRegionDTO>> getAllDepartRegions() {
        return ResponseEntity.ok(departRegionService.findAll());
    }

    @GetMapping("/{departRegionId}")
    public ResponseEntity<DepartRegionDTO> getDepartRegion(
            @PathVariable(name = "departRegionId") final Long departRegionId) {
        return ResponseEntity.ok(departRegionService.get(departRegionId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDepartRegion(
            @RequestBody @Valid final DepartRegionDTO departRegionDTO) {
        final Long createdDepartRegionId = departRegionService.create(departRegionDTO);
        return new ResponseEntity<>(createdDepartRegionId, HttpStatus.CREATED);
    }

    @PutMapping("/{departRegionId}")
    public ResponseEntity<Long> updateDepartRegion(
            @PathVariable(name = "departRegionId") final Long departRegionId,
            @RequestBody @Valid final DepartRegionDTO departRegionDTO) {
        departRegionService.update(departRegionId, departRegionDTO);
        return ResponseEntity.ok(departRegionId);
    }

    @DeleteMapping("/{departRegionId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDepartRegion(
            @PathVariable(name = "departRegionId") final Long departRegionId) {
        departRegionService.delete(departRegionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/meetingValues")
    public ResponseEntity<Map<Long, String>> getMeetingValues() {
        return ResponseEntity.ok(meetingRepository.findAll(Sort.by("meetingId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Meeting::getMeetingId, Meeting::getMeetingPlatform)));
    }

    @GetMapping("/middleRegionValues")
    public ResponseEntity<Map<Long, Long>> getMiddleRegionValues() {
        return ResponseEntity.ok(middleRegionRepository.findAll(Sort.by("middleRegionId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(MiddleRegion::getMiddleRegionId, MiddleRegion::getMiddleRegionId)));
    }

}
