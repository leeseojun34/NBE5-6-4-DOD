package com.grepp.spring.app.model.schedule_member.rest;

import com.grepp.spring.app.model.member.domain.Member;
import com.grepp.spring.app.model.member.repos.MemberRepository;
import com.grepp.spring.app.model.middle_region.domain.MiddleRegion;
import com.grepp.spring.app.model.middle_region.repos.MiddleRegionRepository;
import com.grepp.spring.app.model.schedule.domain.Schedule;
import com.grepp.spring.app.model.schedule.repos.ScheduleRepository;
import com.grepp.spring.app.model.schedule_member.model.ScheduleMemberDTO;
import com.grepp.spring.app.model.schedule_member.service.ScheduleMemberService;
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
@RequestMapping(value = "/api/scheduleMembers", produces = MediaType.APPLICATION_JSON_VALUE)
public class ScheduleMemberResource {

    private final ScheduleMemberService scheduleMemberService;
    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;
    private final MiddleRegionRepository middleRegionRepository;

    public ScheduleMemberResource(final ScheduleMemberService scheduleMemberService,
            final MemberRepository memberRepository, final ScheduleRepository scheduleRepository,
            final MiddleRegionRepository middleRegionRepository) {
        this.scheduleMemberService = scheduleMemberService;
        this.memberRepository = memberRepository;
        this.scheduleRepository = scheduleRepository;
        this.middleRegionRepository = middleRegionRepository;
    }

    @GetMapping
    public ResponseEntity<List<ScheduleMemberDTO>> getAllScheduleMembers() {
        return ResponseEntity.ok(scheduleMemberService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleMemberDTO> getScheduleMember(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(scheduleMemberService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createScheduleMember(
            @RequestBody @Valid final ScheduleMemberDTO scheduleMemberDTO) {
        final Long createdId = scheduleMemberService.create(scheduleMemberDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateScheduleMember(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final ScheduleMemberDTO scheduleMemberDTO) {
        scheduleMemberService.update(id, scheduleMemberDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteScheduleMember(@PathVariable(name = "id") final Long id) {
        scheduleMemberService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/memberValues")
    public ResponseEntity<Map<String, String>> getMemberValues() {
        return ResponseEntity.ok(memberRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Member::getId, Member::getPassword)));
    }

    @GetMapping("/scheduleValues")
    public ResponseEntity<Map<Long, String>> getScheduleValues() {
        return ResponseEntity.ok(scheduleRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Schedule::getId, Schedule::getStatus)));
    }

    @GetMapping("/middleRegionValues")
    public ResponseEntity<Map<Long, Long>> getMiddleRegionValues() {
        return ResponseEntity.ok(middleRegionRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(MiddleRegion::getId, MiddleRegion::getId)));
    }

}
