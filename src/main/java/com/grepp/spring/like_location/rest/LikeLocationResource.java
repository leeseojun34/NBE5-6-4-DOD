package com.grepp.spring.like_location.rest;

import com.grepp.spring.like_location.model.LikeLocationDTO;
import com.grepp.spring.like_location.service.LikeLocationService;
import com.grepp.spring.member.domain.Member;
import com.grepp.spring.member.repos.MemberRepository;
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
@RequestMapping(value = "/api/likeLocations", produces = MediaType.APPLICATION_JSON_VALUE)
public class LikeLocationResource {

    private final LikeLocationService likeLocationService;
    private final MemberRepository memberRepository;

    public LikeLocationResource(final LikeLocationService likeLocationService,
            final MemberRepository memberRepository) {
        this.likeLocationService = likeLocationService;
        this.memberRepository = memberRepository;
    }

    @GetMapping
    public ResponseEntity<List<LikeLocationDTO>> getAllLikeLocations() {
        return ResponseEntity.ok(likeLocationService.findAll());
    }

    @GetMapping("/{likeLocationId}")
    public ResponseEntity<LikeLocationDTO> getLikeLocation(
            @PathVariable(name = "likeLocationId") final Long likeLocationId) {
        return ResponseEntity.ok(likeLocationService.get(likeLocationId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createLikeLocation(
            @RequestBody @Valid final LikeLocationDTO likeLocationDTO) {
        final Long createdLikeLocationId = likeLocationService.create(likeLocationDTO);
        return new ResponseEntity<>(createdLikeLocationId, HttpStatus.CREATED);
    }

    @PutMapping("/{likeLocationId}")
    public ResponseEntity<Long> updateLikeLocation(
            @PathVariable(name = "likeLocationId") final Long likeLocationId,
            @RequestBody @Valid final LikeLocationDTO likeLocationDTO) {
        likeLocationService.update(likeLocationId, likeLocationDTO);
        return ResponseEntity.ok(likeLocationId);
    }

    @DeleteMapping("/{likeLocationId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteLikeLocation(
            @PathVariable(name = "likeLocationId") final Long likeLocationId) {
        likeLocationService.delete(likeLocationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/userValues")
    public ResponseEntity<Map<String, String>> getUserValues() {
        return ResponseEntity.ok(memberRepository.findAll(Sort.by("userId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Member::getUserId, Member::getPassword)));
    }

}
