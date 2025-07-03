package com.grepp.spring.schedule.rest;

import com.grepp.spring.event.domain.Event;
import com.grepp.spring.event.repos.EventRepository;
import com.grepp.spring.schedule.model.ScheduleDTO;
import com.grepp.spring.schedule.service.ScheduleService;
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
@RequestMapping(value = "/api/schedules", produces = MediaType.APPLICATION_JSON_VALUE)
public class ScheduleResource {

    private final ScheduleService scheduleService;
    private final EventRepository eventRepository;

    public ScheduleResource(final ScheduleService scheduleService,
            final EventRepository eventRepository) {
        this.scheduleService = scheduleService;
        this.eventRepository = eventRepository;
    }

    @GetMapping
    public ResponseEntity<List<ScheduleDTO>> getAllSchedules() {
        return ResponseEntity.ok(scheduleService.findAll());
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleDTO> getSchedule(
            @PathVariable(name = "scheduleId") final Long scheduleId) {
        return ResponseEntity.ok(scheduleService.get(scheduleId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createSchedule(@RequestBody @Valid final ScheduleDTO scheduleDTO) {
        final Long createdScheduleId = scheduleService.create(scheduleDTO);
        return new ResponseEntity<>(createdScheduleId, HttpStatus.CREATED);
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<Long> updateSchedule(
            @PathVariable(name = "scheduleId") final Long scheduleId,
            @RequestBody @Valid final ScheduleDTO scheduleDTO) {
        scheduleService.update(scheduleId, scheduleDTO);
        return ResponseEntity.ok(scheduleId);
    }

    @DeleteMapping("/{scheduleId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable(name = "scheduleId") final Long scheduleId) {
        final ReferencedWarning referencedWarning = scheduleService.getReferencedWarning(scheduleId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        scheduleService.delete(scheduleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/eventValues")
    public ResponseEntity<Map<Long, String>> getEventValues() {
        return ResponseEntity.ok(eventRepository.findAll(Sort.by("eventId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Event::getEventId, Event::getTitle)));
    }

}
