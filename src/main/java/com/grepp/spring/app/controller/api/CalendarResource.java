package com.grepp.spring.app.controller.api;

import com.grepp.spring.app.model.calendar.model.CalendarDTO;
import com.grepp.spring.app.model.calendar.service.CalendarService;
import com.grepp.spring.app.model.member.domain.Member;
import com.grepp.spring.app.model.member.repos.MemberRepository;
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
@RequestMapping(value = "/api/calendars", produces = MediaType.APPLICATION_JSON_VALUE)
public class CalendarResource {

    private final CalendarService calendarService;
    private final MemberRepository memberRepository;

    public CalendarResource(final CalendarService calendarService,
            final MemberRepository memberRepository) {
        this.calendarService = calendarService;
        this.memberRepository = memberRepository;
    }

    @GetMapping
    public ResponseEntity<List<CalendarDTO>> getAllCalendars() {
        return ResponseEntity.ok(calendarService.findAll());
    }

    @GetMapping("/{calendarId}")
    public ResponseEntity<CalendarDTO> getCalendar(
            @PathVariable(name = "calendarId") final Long calendarId) {
        return ResponseEntity.ok(calendarService.get(calendarId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createCalendar(@RequestBody @Valid final CalendarDTO calendarDTO) {
        final Long createdCalendarId = calendarService.create(calendarDTO);
        return new ResponseEntity<>(createdCalendarId, HttpStatus.CREATED);
    }

    @PutMapping("/{calendarId}")
    public ResponseEntity<Long> updateCalendar(
            @PathVariable(name = "calendarId") final Long calendarId,
            @RequestBody @Valid final CalendarDTO calendarDTO) {
        calendarService.update(calendarId, calendarDTO);
        return ResponseEntity.ok(calendarId);
    }

    @DeleteMapping("/{calendarId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteCalendar(
            @PathVariable(name = "calendarId") final Long calendarId) {
        final ReferencedWarning referencedWarning = calendarService.getReferencedWarning(calendarId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        calendarService.delete(calendarId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/userValues")
    public ResponseEntity<Map<String, String>> getUserValues() {
        return ResponseEntity.ok(memberRepository.findAll(Sort.by("userId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Member::getUserId, Member::getPassword)));
    }

}
