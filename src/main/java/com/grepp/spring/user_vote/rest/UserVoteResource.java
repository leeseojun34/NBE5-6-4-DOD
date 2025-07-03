package com.grepp.spring.user_vote.rest;

import com.grepp.spring.location_candidate.domain.LocationCandidate;
import com.grepp.spring.location_candidate.repos.LocationCandidateRepository;
import com.grepp.spring.user_vote.model.UserVoteDTO;
import com.grepp.spring.user_vote.service.UserVoteService;
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
@RequestMapping(value = "/api/userVotes", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserVoteResource {

    private final UserVoteService userVoteService;
    private final LocationCandidateRepository locationCandidateRepository;

    public UserVoteResource(final UserVoteService userVoteService,
            final LocationCandidateRepository locationCandidateRepository) {
        this.userVoteService = userVoteService;
        this.locationCandidateRepository = locationCandidateRepository;
    }

    @GetMapping
    public ResponseEntity<List<UserVoteDTO>> getAllUserVotes() {
        return ResponseEntity.ok(userVoteService.findAll());
    }

    @GetMapping("/{userVoteId}")
    public ResponseEntity<UserVoteDTO> getUserVote(
            @PathVariable(name = "userVoteId") final Long userVoteId) {
        return ResponseEntity.ok(userVoteService.get(userVoteId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createUserVote(@RequestBody @Valid final UserVoteDTO userVoteDTO) {
        final Long createdUserVoteId = userVoteService.create(userVoteDTO);
        return new ResponseEntity<>(createdUserVoteId, HttpStatus.CREATED);
    }

    @PutMapping("/{userVoteId}")
    public ResponseEntity<Long> updateUserVote(
            @PathVariable(name = "userVoteId") final Long userVoteId,
            @RequestBody @Valid final UserVoteDTO userVoteDTO) {
        userVoteService.update(userVoteId, userVoteDTO);
        return ResponseEntity.ok(userVoteId);
    }

    @DeleteMapping("/{userVoteId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteUserVote(
            @PathVariable(name = "userVoteId") final Long userVoteId) {
        userVoteService.delete(userVoteId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/locationCandidateValues")
    public ResponseEntity<Map<Long, String>> getLocationCandidateValues() {
        return ResponseEntity.ok(locationCandidateRepository.findAll(Sort.by("locationCandidateId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(LocationCandidate::getLocationCandidateId, LocationCandidate::getSuggestUserId)));
    }

}
