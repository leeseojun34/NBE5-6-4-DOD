package com.grepp.spring.app.controller.api.auth;

import com.grepp.spring.app.controller.api.auth.payload.AccountDeactivateRequest;
import com.grepp.spring.app.controller.api.auth.payload.AccountDeactivateResponse;
import com.grepp.spring.app.controller.api.auth.payload.GroupAdminResponse;
import com.grepp.spring.app.controller.api.auth.payload.LoginRequest;
import com.grepp.spring.app.controller.api.auth.payload.LoginResponse;
import com.grepp.spring.app.controller.api.auth.payload.RegisterRequest;
import com.grepp.spring.app.controller.api.auth.payload.RegisterResponse;
import com.grepp.spring.app.controller.api.auth.payload.SocialAccountConnectionRequest;
import com.grepp.spring.app.controller.api.auth.payload.SocialAccountConnectionResponse;
import com.grepp.spring.app.controller.api.auth.payload.SocialAccountResponse;
import com.grepp.spring.app.controller.api.auth.payload.UpdateAccessTokenResponse;
import com.grepp.spring.app.controller.api.group.groupDto.groupRole.GroupRole;
import com.grepp.spring.app.model.auth.code.Role;
import com.grepp.spring.infra.response.ApiResponse;
import com.grepp.spring.infra.response.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Operation(summary = "회원 가입", description = "소셜 계정으로 회원가입 하는 사용자들에 대한 검증을 진행합니다.")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(201)
            .body(ApiResponse.successCreated(
                new RegisterResponse("GOOGLE_1234", "ROLE_USER", "하명도")));
    }

    @Operation(summary = "로그인", description = "소셜 계정으로 로그인을 진행합니다.")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {

        if (request.getActivated()){
            return ResponseEntity.ok(ApiResponse.success(
                new LoginResponse("GOOGLE_1234", Role.ROLE_USER.name(), "하명도", "eyJhbGciOiJIUzI1NiIsInR5cCI6Ik", "hioKcQ921Nsjns6h2LLAschbnauwd")
            ));
        } else {
            return ResponseEntity.ok(ApiResponse.activateSuccess(
                new LoginResponse("KAKAO_5678", Role.ROLE_USER.name(), "최동준", "eyJhbGciOiJIUzI1NiIsInR5cCI6Ik", "hioKcQ921Nsjns6h2LLAschbnauwd")
            ));
        }
    }

    @Operation(summary = "회원 탈퇴", description = "서비스 탈퇴를 진행합니다.")
    @PatchMapping("/deactivate")
    public ResponseEntity<ApiResponse<?>> deactivate(@Valid @RequestBody AccountDeactivateRequest request) {
        if (request.getGroupRole().equals(GroupRole.GROUP_LEADER)){
            return ResponseEntity.status(403)
                .body(ApiResponse.error(ResponseCode.ADMIN_WITHDRAWAL_NOT_ALLOWED,
                    Map.of("groups", List.of(new GroupAdminResponse("10001", "대나무행주"),
                        new GroupAdminResponse("10003", "긔수씨팬클럽")))));
        } else {
            return ResponseEntity.ok(ApiResponse.success(
                new AccountDeactivateResponse("GOOGLE_1234", "ROLE_USER", "하명도")
            ));
        }
    }

    @Operation(summary = "로그아웃", description = "로그아웃을 진행합니다.")
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<?>> logout() {
        return ResponseEntity.ok(ApiResponse.noContent());
    }

    @Operation(summary = "소셜 연동 조회", description = "계정에 연동된 소셜 계정들을 조회합니다.")
    @GetMapping("/social-connections")
    public ResponseEntity<ApiResponse<?>> socialAccount() {
        return ResponseEntity.ok(ApiResponse.success(
            Map.of("socialAccounts", List.of(new SocialAccountResponse("jgnsjn198283718", Provider.GOOGLE),
                new SocialAccountResponse("892bgdh71hb2dda", Provider.KAKAO)))));
    }

    @Operation(summary = "소셜 연동 요청", description = "계정에 새로운 소셜 계정을 연동합니다."
        + "test AuthorizationCode : dkftndjqtsmsdlswmdzhem123")
    @PostMapping("/social-connections")
    public ResponseEntity<ApiResponse<?>> socialAccountConnections(@Valid @RequestBody SocialAccountConnectionRequest request) {
        if (request.getAuthorizationCode().equals("dkftndjqtsmsdlswmdzhem123")) {
            return ResponseEntity.ok(ApiResponse.success(
                new SocialAccountConnectionResponse(Provider.GOOGLE)
            ));
        } else {
            return ResponseEntity.status(400)
                .body(ApiResponse.error(ResponseCode.BAD_REQUEST));
        }
    }

    @Operation(summary = "엑세스 토큰 갱신", description = "리프레시 토큰을 보내 엑세스 토큰을 갱신합니다.")
    @PostMapping("/update-tokens")
    public ResponseEntity<ApiResponse<?>> updateAccessToken() {
        return ResponseEntity.ok(ApiResponse.success(
            new UpdateAccessTokenResponse("eyJhbGciOiJIUzI1NiIsInR5cCI6Ik",
                "hioKcQ921Nsjns6h2LLAschbnauwd")
        ));
    }


}
