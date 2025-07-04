package com.grepp.spring.app.model.temp_schedule.rest;

import com.grepp.spring.app.model.event_member.domain.EventMember;
import com.grepp.spring.app.model.event_member.repos.EventMemberRepository;
import com.grepp.spring.app.model.temp_schedule.model.TempScheduleDTO;
import com.grepp.spring.app.model.temp_schedule.service.TempScheduleService;
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
@RequestMapping(value = "/api/tempSchedules", produces = MediaType.APPLICATION_JSON_VALUE)
public class TempScheduleResource {

    private final TempScheduleService tempScheduleService;
    private final EventMemberRepository eventMemberRepository;

    public TempScheduleResource(final TempScheduleService tempScheduleService,
            final EventMemberRepository eventMemberRepository) {
        this.tempScheduleService = tempScheduleService;
        this.eventMemberRepository = eventMemberRepository;
    }

    @GetMapping
    public ResponseEntity<List<TempScheduleDTO>> getAllTempSchedules() {
        return ResponseEntity.ok(tempScheduleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TempScheduleDTO> getTempSchedule(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(tempScheduleService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createTempSchedule(
            @RequestBody @Valid final TempScheduleDTO tempScheduleDTO) {
        final Long createdId = tempScheduleService.create(tempScheduleDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateTempSchedule(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final TempScheduleDTO tempScheduleDTO) {
        tempScheduleService.update(id, tempScheduleDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTempSchedule(@PathVariable(name = "id") final Long id) {
        tempScheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/eventMemberValues")
    public ResponseEntity<Map<Long, String>> getEventMemberValues() {
        return ResponseEntity.ok(eventMemberRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(EventMember::getId, EventMember::getRole)));
    }

}
