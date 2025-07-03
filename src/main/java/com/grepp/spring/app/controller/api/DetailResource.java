package com.grepp.spring.app.controller.api;

import com.grepp.spring.app.model.detail.model.DetailDTO;
import com.grepp.spring.app.model.detail.service.DetailService;
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
@RequestMapping(value = "/api/details", produces = MediaType.APPLICATION_JSON_VALUE)
public class DetailResource {

    private final DetailService detailService;
    private final ScheduleRepository scheduleRepository;

    public DetailResource(final DetailService detailService,
            final ScheduleRepository scheduleRepository) {
        this.detailService = detailService;
        this.scheduleRepository = scheduleRepository;
    }

    @GetMapping
    public ResponseEntity<List<DetailDTO>> getAllDetails() {
        return ResponseEntity.ok(detailService.findAll());
    }

    @GetMapping("/{detailId}")
    public ResponseEntity<DetailDTO> getDetail(
            @PathVariable(name = "detailId") final Long detailId) {
        return ResponseEntity.ok(detailService.get(detailId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDetail(@RequestBody @Valid final DetailDTO detailDTO) {
        final Long createdDetailId = detailService.create(detailDTO);
        return new ResponseEntity<>(createdDetailId, HttpStatus.CREATED);
    }

    @PutMapping("/{detailId}")
    public ResponseEntity<Long> updateDetail(@PathVariable(name = "detailId") final Long detailId,
            @RequestBody @Valid final DetailDTO detailDTO) {
        detailService.update(detailId, detailDTO);
        return ResponseEntity.ok(detailId);
    }

    @DeleteMapping("/{detailId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDetail(@PathVariable(name = "detailId") final Long detailId) {
        final ReferencedWarning referencedWarning = detailService.getReferencedWarning(detailId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        detailService.delete(detailId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/scheduleValues")
    public ResponseEntity<Map<Long, String>> getScheduleValues() {
        return ResponseEntity.ok(scheduleRepository.findAll(Sort.by("scheduleId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Schedule::getScheduleId, Schedule::getStatus)));
    }

}
