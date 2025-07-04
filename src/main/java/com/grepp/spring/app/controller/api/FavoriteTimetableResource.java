package com.grepp.spring.app.controller.api;

import com.grepp.spring.app.model.favorite_timetable.model.FavoriteTimetableDTO;
import com.grepp.spring.app.model.favorite_timetable.service.FavoriteTimetableService;
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
@RequestMapping(value = "/api/favoriteTimetables", produces = MediaType.APPLICATION_JSON_VALUE)
public class FavoriteTimetableResource {

    private final FavoriteTimetableService favoriteTimetableService;
    private final MemberRepository memberRepository;

    public FavoriteTimetableResource(final FavoriteTimetableService favoriteTimetableService,
            final MemberRepository memberRepository) {
        this.favoriteTimetableService = favoriteTimetableService;
        this.memberRepository = memberRepository;
    }

    @GetMapping
    public ResponseEntity<List<FavoriteTimetableDTO>> getAllFavoriteTimetables() {
        return ResponseEntity.ok(favoriteTimetableService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FavoriteTimetableDTO> getFavoriteTimetable(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(favoriteTimetableService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createFavoriteTimetable(
            @RequestBody @Valid final FavoriteTimetableDTO favoriteTimetableDTO) {
        final Long createdId = favoriteTimetableService.create(favoriteTimetableDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateFavoriteTimetable(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final FavoriteTimetableDTO favoriteTimetableDTO) {
        favoriteTimetableService.update(id, favoriteTimetableDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteFavoriteTimetable(@PathVariable(name = "id") final Long id) {
        favoriteTimetableService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/memberValues")
    public ResponseEntity<Map<String, String>> getMemberValues() {
        return ResponseEntity.ok(memberRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Member::getId, Member::getPassword)));
    }

}
