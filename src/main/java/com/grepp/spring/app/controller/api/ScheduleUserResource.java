package com.grepp.spring.app.controller.api;

import com.grepp.spring.app.model.member.domain.Member;
import com.grepp.spring.app.model.member.repos.MemberRepository;
import com.grepp.spring.app.model.schedule.domain.Schedule;
import com.grepp.spring.app.model.schedule.repos.ScheduleRepository;
import com.grepp.spring.app.model.schedule_user.model.ScheduleUserDTO;
import com.grepp.spring.app.model.schedule_user.service.ScheduleUserService;
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
@RequestMapping(value = "/api/scheduleUsers", produces = MediaType.APPLICATION_JSON_VALUE)
public class ScheduleUserResource {

    private final ScheduleUserService scheduleUserService;
    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;

    public ScheduleUserResource(final ScheduleUserService scheduleUserService,
            final MemberRepository memberRepository, final ScheduleRepository scheduleRepository) {
        this.scheduleUserService = scheduleUserService;
        this.memberRepository = memberRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @GetMapping
    public ResponseEntity<List<ScheduleUserDTO>> getAllScheduleUsers() {
        return ResponseEntity.ok(scheduleUserService.findAll());
    }

    @GetMapping("/{scheduleUserId}")
    public ResponseEntity<ScheduleUserDTO> getScheduleUser(
            @PathVariable(name = "scheduleUserId") final Long scheduleUserId) {
        return ResponseEntity.ok(scheduleUserService.get(scheduleUserId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createScheduleUser(
            @RequestBody @Valid final ScheduleUserDTO scheduleUserDTO) {
        final Long createdScheduleUserId = scheduleUserService.create(scheduleUserDTO);
        return new ResponseEntity<>(createdScheduleUserId, HttpStatus.CREATED);
    }

    @PutMapping("/{scheduleUserId}")
    public ResponseEntity<Long> updateScheduleUser(
            @PathVariable(name = "scheduleUserId") final Long scheduleUserId,
            @RequestBody @Valid final ScheduleUserDTO scheduleUserDTO) {
        scheduleUserService.update(scheduleUserId, scheduleUserDTO);
        return ResponseEntity.ok(scheduleUserId);
    }

    @DeleteMapping("/{scheduleUserId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteScheduleUser(
            @PathVariable(name = "scheduleUserId") final Long scheduleUserId) {
        scheduleUserService.delete(scheduleUserId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/userValues")
    public ResponseEntity<Map<String, String>> getUserValues() {
        return ResponseEntity.ok(memberRepository.findAll(Sort.by("userId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Member::getUserId, Member::getPassword)));
    }

    @GetMapping("/scheduleValues")
    public ResponseEntity<Map<Long, String>> getScheduleValues() {
        return ResponseEntity.ok(scheduleRepository.findAll(Sort.by("scheduleId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Schedule::getScheduleId, Schedule::getStatus)));
    }

}
