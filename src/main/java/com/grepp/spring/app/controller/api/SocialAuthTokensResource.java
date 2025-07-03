package com.grepp.spring.app.controller.api;

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

    @GetMapping("/{socialAuthTokensId}")
    public ResponseEntity<SocialAuthTokensDTO> getSocialAuthTokens(
            @PathVariable(name = "socialAuthTokensId") final Long socialAuthTokensId) {
        return ResponseEntity.ok(socialAuthTokensService.get(socialAuthTokensId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createSocialAuthTokens(
            @RequestBody @Valid final SocialAuthTokensDTO socialAuthTokensDTO) {
        final Long createdSocialAuthTokensId = socialAuthTokensService.create(socialAuthTokensDTO);
        return new ResponseEntity<>(createdSocialAuthTokensId, HttpStatus.CREATED);
    }

    @PutMapping("/{socialAuthTokensId}")
    public ResponseEntity<Long> updateSocialAuthTokens(
            @PathVariable(name = "socialAuthTokensId") final Long socialAuthTokensId,
            @RequestBody @Valid final SocialAuthTokensDTO socialAuthTokensDTO) {
        socialAuthTokensService.update(socialAuthTokensId, socialAuthTokensDTO);
        return ResponseEntity.ok(socialAuthTokensId);
    }

    @DeleteMapping("/{socialAuthTokensId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSocialAuthTokens(
            @PathVariable(name = "socialAuthTokensId") final Long socialAuthTokensId) {
        socialAuthTokensService.delete(socialAuthTokensId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/userValues")
    public ResponseEntity<Map<String, String>> getUserValues() {
        return ResponseEntity.ok(memberRepository.findAll(Sort.by("userId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Member::getUserId, Member::getPassword)));
    }

}
