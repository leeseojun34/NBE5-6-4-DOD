package com.grepp.spring.app.model.calendar_detail.rest;

import com.grepp.spring.app.model.calendar.domain.Calendar;
import com.grepp.spring.app.model.calendar.repos.CalendarRepository;
import com.grepp.spring.app.model.calendar_detail.model.CalendarDetailDTO;
import com.grepp.spring.app.model.calendar_detail.service.CalendarDetailService;
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
@RequestMapping(value = "/api/calendarDetails", produces = MediaType.APPLICATION_JSON_VALUE)
public class CalendarDetailResource {

    private final CalendarDetailService calendarDetailService;
    private final CalendarRepository calendarRepository;

    public CalendarDetailResource(final CalendarDetailService calendarDetailService,
            final CalendarRepository calendarRepository) {
        this.calendarDetailService = calendarDetailService;
        this.calendarRepository = calendarRepository;
    }

    @GetMapping
    public ResponseEntity<List<CalendarDetailDTO>> getAllCalendarDetails() {
        return ResponseEntity.ok(calendarDetailService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CalendarDetailDTO> getCalendarDetail(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(calendarDetailService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createCalendarDetail(
            @RequestBody @Valid final CalendarDetailDTO calendarDetailDTO) {
        final Long createdId = calendarDetailService.create(calendarDetailDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateCalendarDetail(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final CalendarDetailDTO calendarDetailDTO) {
        calendarDetailService.update(id, calendarDetailDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteCalendarDetail(@PathVariable(name = "id") final Long id) {
        calendarDetailService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/calendarValues")
    public ResponseEntity<Map<Long, String>> getCalendarValues() {
        return ResponseEntity.ok(calendarRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Calendar::getId, Calendar::getName)));
    }

}
