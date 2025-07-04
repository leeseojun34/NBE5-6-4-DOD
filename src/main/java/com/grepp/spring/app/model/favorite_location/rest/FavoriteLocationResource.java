package com.grepp.spring.app.model.favorite_location.rest;

import com.grepp.spring.app.model.favorite_location.model.FavoriteLocationDTO;
import com.grepp.spring.app.model.favorite_location.service.FavoriteLocationService;
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
@RequestMapping(value = "/api/favoriteLocations", produces = MediaType.APPLICATION_JSON_VALUE)
public class FavoriteLocationResource {

    private final FavoriteLocationService favoriteLocationService;
    private final MemberRepository memberRepository;

    public FavoriteLocationResource(final FavoriteLocationService favoriteLocationService,
            final MemberRepository memberRepository) {
        this.favoriteLocationService = favoriteLocationService;
        this.memberRepository = memberRepository;
    }

    @GetMapping
    public ResponseEntity<List<FavoriteLocationDTO>> getAllFavoriteLocations() {
        return ResponseEntity.ok(favoriteLocationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FavoriteLocationDTO> getFavoriteLocation(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(favoriteLocationService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createFavoriteLocation(
            @RequestBody @Valid final FavoriteLocationDTO favoriteLocationDTO) {
        final Long createdId = favoriteLocationService.create(favoriteLocationDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateFavoriteLocation(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final FavoriteLocationDTO favoriteLocationDTO) {
        favoriteLocationService.update(id, favoriteLocationDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteFavoriteLocation(@PathVariable(name = "id") final Long id) {
        favoriteLocationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/memberValues")
    public ResponseEntity<Map<String, String>> getMemberValues() {
        return ResponseEntity.ok(memberRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Member::getId, Member::getPassword)));
    }

}
