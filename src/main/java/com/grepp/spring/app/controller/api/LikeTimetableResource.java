package com.grepp.spring.app.controller.api;

import com.grepp.spring.app.model.like_timetable.model.LikeTimetableDTO;
import com.grepp.spring.app.model.like_timetable.service.LikeTimetableService;
import com.grepp.spring.app.model.member.domain.Member;
import com.grepp.spring.app.model.member.repos.MemberRepository;
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
@RequestMapping(value = "/api/likeTimetables", produces = MediaType.APPLICATION_JSON_VALUE)
public class LikeTimetableResource {

    private final LikeTimetableService likeTimetableService;
    private final MemberRepository memberRepository;

    public LikeTimetableResource(final LikeTimetableService likeTimetableService,
            final MemberRepository memberRepository) {
        this.likeTimetableService = likeTimetableService;
        this.memberRepository = memberRepository;
    }

    @GetMapping
    public ResponseEntity<List<LikeTimetableDTO>> getAllLikeTimetables() {
        return ResponseEntity.ok(likeTimetableService.findAll());
    }

    @GetMapping("/{likeTimetableId}")
    public ResponseEntity<LikeTimetableDTO> getLikeTimetable(
            @PathVariable(name = "likeTimetableId") final Long likeTimetableId) {
        return ResponseEntity.ok(likeTimetableService.get(likeTimetableId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createLikeTimetable(
            @RequestBody @Valid final LikeTimetableDTO likeTimetableDTO) {
        final Long createdLikeTimetableId = likeTimetableService.create(likeTimetableDTO);
        return new ResponseEntity<>(createdLikeTimetableId, HttpStatus.CREATED);
    }

    @PutMapping("/{likeTimetableId}")
    public ResponseEntity<Long> updateLikeTimetable(
            @PathVariable(name = "likeTimetableId") final Long likeTimetableId,
            @RequestBody @Valid final LikeTimetableDTO likeTimetableDTO) {
        likeTimetableService.update(likeTimetableId, likeTimetableDTO);
        return ResponseEntity.ok(likeTimetableId);
    }

    @DeleteMapping("/{likeTimetableId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteLikeTimetable(
            @PathVariable(name = "likeTimetableId") final Long likeTimetableId) {
        likeTimetableService.delete(likeTimetableId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/userValues")
    public ResponseEntity<Map<String, String>> getUserValues() {
        return ResponseEntity.ok(memberRepository.findAll(Sort.by("userId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Member::getUserId, Member::getPassword)));
    }

}
