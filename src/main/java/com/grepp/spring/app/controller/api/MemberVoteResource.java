package com.grepp.spring.app.controller.api;

import com.grepp.spring.app.model.location.domain.Location;
import com.grepp.spring.app.model.location.repos.LocationRepository;
import com.grepp.spring.app.model.member_vote.model.MemberVoteDTO;
import com.grepp.spring.app.model.member_vote.service.MemberVoteService;
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
@RequestMapping(value = "/api/memberVotes", produces = MediaType.APPLICATION_JSON_VALUE)
public class MemberVoteResource {

    private final MemberVoteService memberVoteService;
    private final LocationRepository locationRepository;

    public MemberVoteResource(final MemberVoteService memberVoteService,
            final LocationRepository locationRepository) {
        this.memberVoteService = memberVoteService;
        this.locationRepository = locationRepository;
    }

    @GetMapping
    public ResponseEntity<List<MemberVoteDTO>> getAllMemberVotes() {
        return ResponseEntity.ok(memberVoteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberVoteDTO> getMemberVote(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(memberVoteService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createMemberVote(
            @RequestBody @Valid final MemberVoteDTO memberVoteDTO) {
        final Long createdId = memberVoteService.create(memberVoteDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateMemberVote(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final MemberVoteDTO memberVoteDTO) {
        memberVoteService.update(id, memberVoteDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMemberVote(@PathVariable(name = "id") final Long id) {
        memberVoteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/locationValues")
    public ResponseEntity<Map<Long, String>> getLocationValues() {
        return ResponseEntity.ok(locationRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Location::getId, Location::getName)));
    }

}
