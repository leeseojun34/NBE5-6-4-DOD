package com.grepp.spring.app.controller.api;

import com.grepp.spring.app.model.event_user.domain.EventUser;
import com.grepp.spring.app.model.event_user.repos.EventUserRepository;
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
    private final EventUserRepository eventUserRepository;

    public TempScheduleResource(final TempScheduleService tempScheduleService,
            final EventUserRepository eventUserRepository) {
        this.tempScheduleService = tempScheduleService;
        this.eventUserRepository = eventUserRepository;
    }

    @GetMapping
    public ResponseEntity<List<TempScheduleDTO>> getAllTempSchedules() {
        return ResponseEntity.ok(tempScheduleService.findAll());
    }

    @GetMapping("/{tempScheduleId}")
    public ResponseEntity<TempScheduleDTO> getTempSchedule(
            @PathVariable(name = "tempScheduleId") final Long tempScheduleId) {
        return ResponseEntity.ok(tempScheduleService.get(tempScheduleId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createTempSchedule(
            @RequestBody @Valid final TempScheduleDTO tempScheduleDTO) {
        final Long createdTempScheduleId = tempScheduleService.create(tempScheduleDTO);
        return new ResponseEntity<>(createdTempScheduleId, HttpStatus.CREATED);
    }

    @PutMapping("/{tempScheduleId}")
    public ResponseEntity<Long> updateTempSchedule(
            @PathVariable(name = "tempScheduleId") final Long tempScheduleId,
            @RequestBody @Valid final TempScheduleDTO tempScheduleDTO) {
        tempScheduleService.update(tempScheduleId, tempScheduleDTO);
        return ResponseEntity.ok(tempScheduleId);
    }

    @DeleteMapping("/{tempScheduleId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTempSchedule(
            @PathVariable(name = "tempScheduleId") final Long tempScheduleId) {
        tempScheduleService.delete(tempScheduleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/eventUserValues")
    public ResponseEntity<Map<Long, Long>> getEventUserValues() {
        return ResponseEntity.ok(eventUserRepository.findAll(Sort.by("eventUserId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(EventUser::getEventUserId, EventUser::getEventUserId)));
    }

}
