package com.grepp.spring.app.controller.api;

import com.grepp.spring.app.model.candidate_date.model.CandidateDateDTO;
import com.grepp.spring.app.model.candidate_date.service.CandidateDateService;
import com.grepp.spring.app.model.event.domain.Event;
import com.grepp.spring.app.model.event.repos.EventRepository;
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
@RequestMapping(value = "/api/candidateDates", produces = MediaType.APPLICATION_JSON_VALUE)
public class CandidateDateResource {

    private final CandidateDateService candidateDateService;
    private final EventRepository eventRepository;

    public CandidateDateResource(final CandidateDateService candidateDateService,
            final EventRepository eventRepository) {
        this.candidateDateService = candidateDateService;
        this.eventRepository = eventRepository;
    }

    @GetMapping
    public ResponseEntity<List<CandidateDateDTO>> getAllCandidateDates() {
        return ResponseEntity.ok(candidateDateService.findAll());
    }

    @GetMapping("/{candidateDateId}")
    public ResponseEntity<CandidateDateDTO> getCandidateDate(
            @PathVariable(name = "candidateDateId") final Long candidateDateId) {
        return ResponseEntity.ok(candidateDateService.get(candidateDateId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createCandidateDate(
            @RequestBody @Valid final CandidateDateDTO candidateDateDTO) {
        final Long createdCandidateDateId = candidateDateService.create(candidateDateDTO);
        return new ResponseEntity<>(createdCandidateDateId, HttpStatus.CREATED);
    }

    @PutMapping("/{candidateDateId}")
    public ResponseEntity<Long> updateCandidateDate(
            @PathVariable(name = "candidateDateId") final Long candidateDateId,
            @RequestBody @Valid final CandidateDateDTO candidateDateDTO) {
        candidateDateService.update(candidateDateId, candidateDateDTO);
        return ResponseEntity.ok(candidateDateId);
    }

    @DeleteMapping("/{candidateDateId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteCandidateDate(
            @PathVariable(name = "candidateDateId") final Long candidateDateId) {
        candidateDateService.delete(candidateDateId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/eventValues")
    public ResponseEntity<Map<Long, String>> getEventValues() {
        return ResponseEntity.ok(eventRepository.findAll(Sort.by("eventId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Event::getEventId, Event::getTitle)));
    }

}
