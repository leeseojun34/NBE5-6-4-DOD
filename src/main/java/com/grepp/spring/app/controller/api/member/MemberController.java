package com.grepp.spring.app.controller.api.member;

import com.grepp.spring.app.controller.api.member.payload.MemberResponseDto;
import com.grepp.spring.app.model.member.domain.Member;
import com.grepp.spring.app.model.member.service.MemberService;
import com.grepp.spring.infra.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "사용자 정보 조회")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<?>> memberInfo(Authentication authentication){

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Member member = memberService.findById(username).orElseThrow();

        MemberResponseDto memberResponseDto = MemberResponseDto.builder()
            .id(member.getId())
            .name(member.getName())
            .email(member.getEmail())
            .role(member.getRole().name())
            .profileImageNumber(member.getProfileImageNumber())
            .provider(member.getProvider())
            .build();
        return ResponseEntity.ok(ApiResponse.success(memberResponseDto));
    }
}
