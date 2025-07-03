package com.grepp.spring.app.controller.api;

import com.grepp.spring.app.model.meeting.model.MeetingDTO;
import com.grepp.spring.app.model.meeting.service.MeetingService;
import com.grepp.spring.app.model.schedule.domain.Schedule;
import com.grepp.spring.app.model.schedule.repos.ScheduleRepository;
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
@RequestMapping(value = "/api/meetings", produces = MediaType.APPLICATION_JSON_VALUE)
public class MeetingResource {

    private final MeetingService meetingService;
    private final ScheduleRepository scheduleRepository;

    public MeetingResource(final MeetingService meetingService,
            final ScheduleRepository scheduleRepository) {
        this.meetingService = meetingService;
        this.scheduleRepository = scheduleRepository;
    }

    @GetMapping
    public ResponseEntity<List<MeetingDTO>> getAllMeetings() {
        return ResponseEntity.ok(meetingService.findAll());
    }

    @GetMapping("/{meetingId}")
    public ResponseEntity<MeetingDTO> getMeeting(
            @PathVariable(name = "meetingId") final Long meetingId) {
        return ResponseEntity.ok(meetingService.get(meetingId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createMeeting(@RequestBody @Valid final MeetingDTO meetingDTO) {
        final Long createdMeetingId = meetingService.create(meetingDTO);
        return new ResponseEntity<>(createdMeetingId, HttpStatus.CREATED);
    }

    @PutMapping("/{meetingId}")
    public ResponseEntity<Long> updateMeeting(
            @PathVariable(name = "meetingId") final Long meetingId,
            @RequestBody @Valid final MeetingDTO meetingDTO) {
        meetingService.update(meetingId, meetingDTO);
        return ResponseEntity.ok(meetingId);
    }

    @DeleteMapping("/{meetingId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMeeting(
            @PathVariable(name = "meetingId") final Long meetingId) {
        final ReferencedWarning referencedWarning = meetingService.getReferencedWarning(meetingId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        meetingService.delete(meetingId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/scheduleValues")
    public ResponseEntity<Map<Long, String>> getScheduleValues() {
        return ResponseEntity.ok(scheduleRepository.findAll(Sort.by("scheduleId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Schedule::getScheduleId, Schedule::getStatus)));
    }

}
