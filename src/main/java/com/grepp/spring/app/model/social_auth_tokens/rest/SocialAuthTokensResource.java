package com.grepp.spring.app.model.social_auth_tokens.rest;

import com.grepp.spring.app.model.member.domain.Member;
import com.grepp.spring.app.model.member.repos.MemberRepository;
import com.grepp.spring.app.model.social_auth_tokens.model.SocialAuthTokensDTO;
import com.grepp.spring.app.model.social_auth_tokens.service.SocialAuthTokensService;
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
@RequestMapping(value = "/api/socialAuthTokenss", produces = MediaType.APPLICATION_JSON_VALUE)
public class SocialAuthTokensResource {

    private final SocialAuthTokensService socialAuthTokensService;
    private final MemberRepository memberRepository;

    public SocialAuthTokensResource(final SocialAuthTokensService socialAuthTokensService,
            final MemberRepository memberRepository) {
        this.socialAuthTokensService = socialAuthTokensService;
        this.memberRepository = memberRepository;
    }

    @GetMapping
    public ResponseEntity<List<SocialAuthTokensDTO>> getAllSocialAuthTokenss() {
        return ResponseEntity.ok(socialAuthTokensService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocialAuthTokensDTO> getSocialAuthTokens(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(socialAuthTokensService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createSocialAuthTokens(
            @RequestBody @Valid final SocialAuthTokensDTO socialAuthTokensDTO) {
        final Long createdId = socialAuthTokensService.create(socialAuthTokensDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateSocialAuthTokens(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final SocialAuthTokensDTO socialAuthTokensDTO) {
        socialAuthTokensService.update(id, socialAuthTokensDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSocialAuthTokens(@PathVariable(name = "id") final Long id) {
        socialAuthTokensService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/memberValues")
    public ResponseEntity<Map<String, String>> getMemberValues() {
        return ResponseEntity.ok(memberRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Member::getId, Member::getPassword)));
    }

}
