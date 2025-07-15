package com.grepp.spring.app.controller.api.member;

import com.grepp.spring.app.controller.api.member.payload.MemberResponseDto;
import com.grepp.spring.app.controller.api.mypage.payload.ModifyProfileResponse;
import com.grepp.spring.app.model.member.domain.Member;
import com.grepp.spring.app.model.member.service.MemberService;
import com.grepp.spring.infra.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "사용자 정보 조회")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<?>> memberInfo(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        Member member = memberService.findById(userId).orElseThrow();

        MemberResponseDto memberResponseDto = MemberResponseDto.builder()
            .id(member.getId())
            .name(member.getName())
            .email(member.getEmail())
            .role(member.getRole().name())
            .profileImageNumber(member.getProfileImageNumber())
            .provider(member.getProvider().name())
            .build();
        return ResponseEntity.ok(ApiResponse.success(memberResponseDto));
    }

    // 프로필 수정 (사진 + 이름 수정)
    @Operation(summary = "회원 정보 수정", description = "회원 이름을 입력하여 수정할 수 있습니다."
        + "프로필 캐릭터는 랜덤으로 변경됩니다."
        + "\n 나중에 2자 이상 10자 이하에 영문 한글, 양옆 제외 공백 가능 조건 넣을 생각입니다.")
    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<ModifyProfileResponse>> modifyProfile(String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        ModifyProfileResponse response = new ModifyProfileResponse();
        response.setId(userId);
        response.setName(username);
        response.setProfileImageNumber((long) new Random().nextInt(10));

        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
